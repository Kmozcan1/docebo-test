package com.kmozcan1.docebotest.usecase

import com.kmozcan1.docebotest.domain.DomainConstants.PER_PAGE
import com.kmozcan1.docebotest.domain.enums.SortDirection
import com.kmozcan1.docebotest.domain.enums.SortType
import com.kmozcan1.docebotest.domain.mapper.RepositoryMapper
import com.kmozcan1.docebotest.domain.model.RepositoriesResultModel
import com.kmozcan1.docebotest.domain.repository.UsersRepository
import com.kmozcan1.docebotest.usecase.base.ObservableUseCase
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 05-Jan-21.
 */
class GetUserRepositoriesUseCase @Inject constructor(
        private val usersRepository: UsersRepository,
        private val repositoryMapper: RepositoryMapper
): ObservableUseCase<RepositoriesResultModel, GetUserRepositoriesUseCase.Params>() {
    data class Params(val userName: String,
                      val sortType: SortType = SortType.ALPHABETIC,
                      val sortDirection: SortDirection = SortDirection.ASCENDING)

    private var page: Int = 0

    private lateinit var userName: String
    private lateinit var sort: String
    private lateinit var sortDirection: String

    private lateinit var repositoryListSubject: PublishSubject<RepositoriesResultModel>

    // Makes the initial search and subscribes the observable
    // after ViewModel calls the execute function of ObservableUseCase
    override fun buildObservable(params: Params?): Observable<RepositoriesResultModel> {
        repositoryListSubject = PublishSubject.create()
        page = 1
        params?.let {
            userName = it.userName
            sort = it.sortType.stringValue
            sortDirection = it.sortDirection.stringValue
        }
        return repositoryListSubject
    }

    // Searches the next page
    fun loadMore() {
        page++
        getRepositories()
    }

    fun getRepositories() {
        // Calls repository method to fetch the user repositories
        usersRepository.getUserRepositories(
                userName, sort, sortDirection, page, PER_PAGE
        ).map { repositoryList ->
            // Maps each api model in the list to domain model
            repositoryList.map { repository ->
                repositoryMapper.map(repository)
            }
        }.doOnSuccess { repositoryList ->
            // If the list size is smaller than PER_PAGE, it means there are no more results left
            if (repositoryList.size < PER_PAGE) {
                repositoryListSubject.onNext(
                        RepositoriesResultModel(repositoryList, true))
                // Complete on the last page in order to dispose of the observer
                repositoryListSubject.onComplete()
            } else {
                repositoryListSubject.onNext(
                        RepositoriesResultModel(repositoryList, false))
                }
        }.subscribe({}, { t ->
            if (!isDisposed) {
                repositoryListSubject.onError(t)
            }
        })
    }
}