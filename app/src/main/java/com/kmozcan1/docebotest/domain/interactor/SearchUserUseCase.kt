package com.kmozcan1.docebotest.domain.interactor

import com.kmozcan1.docebotest.domain.interactor.base.ObservableUseCase
import com.kmozcan1.docebotest.domain.mapper.SearchResultMapper
import com.kmozcan1.docebotest.domain.model.UserListItem
import com.kmozcan1.docebotest.domain.model.UserSearchResult
import com.kmozcan1.docebotest.domain.repository.SearchRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import io.reactivex.rxjava3.subjects.ReplaySubject
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 30-Dec-20.
 */
class SearchUserUseCase @Inject constructor(
        private val searchRepository: SearchRepository,
        private val searchResultMapper: SearchResultMapper
): ObservableUseCase<UserSearchResult, SearchUserUseCase.Params>() {
    data class Params(val userName: String)

    companion object {
        private const val PER_PAGE = 25
    }

    private var page: Int = 0

    private lateinit var userName: String

    private val userListSubject: PublishSubject<UserSearchResult> by lazy {
        PublishSubject.create()
    }

    // Makes the initial search after ViewModel calls the execute function of ObservableUseCase
    override fun buildObservable(params: Params?): Observable<UserSearchResult> {
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
        searchRepository.searchUser(userName, page, PER_PAGE).map { searchResultList ->
            searchResultList.map { searchResult ->
                searchResultMapper.map(searchResult)
            }
        }.doOnSuccess { userList ->
            // If the list size is smaller than PER_PAGE, it means there are no more results left
            if (userList.size < PER_PAGE) {
                userListSubject.onNext(UserSearchResult(userList, true))
                // Complete on the last page in order to dispose of the observer
                userListSubject.onComplete()
            } else {
                userListSubject.onNext(UserSearchResult(userList, false))
            }
        }.subscribe({}, { t ->
            if (!isDisposed) {
                userListSubject.onError(t)
            }
        })
    }
}