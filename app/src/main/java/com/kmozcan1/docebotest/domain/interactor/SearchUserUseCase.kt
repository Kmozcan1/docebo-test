package com.kmozcan1.docebotest.domain.interactor

import com.kmozcan1.docebotest.domain.DomainConstants.PER_PAGE
import com.kmozcan1.docebotest.domain.interactor.base.ObservableUseCase
import com.kmozcan1.docebotest.domain.mapper.SearchResultMapper
import com.kmozcan1.docebotest.domain.model.UserSearchResultModel
import com.kmozcan1.docebotest.domain.repository.SearchRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 30-Dec-20.
 */
class SearchUserUseCase @Inject constructor(
        private val searchRepository: SearchRepository,
        private val searchResultMapper: SearchResultMapper
): ObservableUseCase<UserSearchResultModel, SearchUserUseCase.Params>() {
    data class Params(val userName: String)

    private var page: Int = 0

    private lateinit var userName: String

    private lateinit var userListSubject: PublishSubject<UserSearchResultModel>

    // Makes the initial search and subscribes the observable
    // after ViewModel calls the execute function of ObservableUseCase
    override fun buildObservable(params: Params?): Observable<UserSearchResultModel> {
        userListSubject = PublishSubject.create()
        page = 1
        userName = params!!.userName
        return userListSubject
    }

    // Searches the next page
    fun loadMore() {
        page++
        searchUser()
    }

    fun searchUser() {
        // Calls repository method to fetch the users
        searchRepository.searchUser(userName, page, PER_PAGE).map { searchResultList ->
            // Maps each api model in the list to domain model
            searchResultList.map { searchResult ->
                searchResultMapper.map(searchResult)
            }
        }.doOnSuccess { userList ->
            // If the list size is smaller than PER_PAGE, it means there are no more results left
            if (userList.size < PER_PAGE) {
                userListSubject.onNext(UserSearchResultModel(userList, true))
                // Complete on the last page in order to dispose of the observer
                userListSubject.onComplete()
            } else {
                userListSubject.onNext(UserSearchResultModel(userList, false))
            }
        }.subscribe({}, { t ->
            if (!isDisposed) {
                userListSubject.onError(t)
            }
        })
    }
}