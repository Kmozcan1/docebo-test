package com.kmozcan1.docebotest.domain.interactor

import com.kmozcan1.docebotest.domain.interactor.base.SingleUseCase
import com.kmozcan1.docebotest.domain.mapper.SearchResultMapper
import com.kmozcan1.docebotest.domain.model.UserSearchListModel
import com.kmozcan1.docebotest.domain.repository.SearchRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 30-Dec-20.
 */
class SearchUserUseCase @Inject constructor(
        private val searchRepository: SearchRepository,
        private val searchResultMapper: SearchResultMapper
): SingleUseCase<List<UserSearchListModel>, SearchUserUseCase.Params>() {
    data class Params(val userName: String, val page: Int)

    override fun buildObservable(params: Params?): Single<List<UserSearchListModel>> {
        return searchRepository.searchUser(params!!.userName, params.page).map { searchResultList ->
            searchResultList.map { searchResult ->
                searchResultMapper.map(searchResult)
            }
        }
    }
}