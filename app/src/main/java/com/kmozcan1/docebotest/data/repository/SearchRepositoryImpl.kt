package com.kmozcan1.docebotest.data.repository

import com.kmozcan1.docebotest.data.api.SearchApi
import com.kmozcan1.docebotest.data.apimodel.UserSearchResult
import com.kmozcan1.docebotest.domain.repository.SearchRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 30-Dec-20.
 *
 * Search repository implementation. Uses functions provided by [SearchApi]
 */
class SearchRepositoryImpl @Inject constructor(
        private val searchApi: SearchApi
) : SearchRepository {
    /**
     * Returns a [Single] object that emits the result returned by the [SearchApi.searchUsers] method
     */
    override fun searchUser(userName: String, page: Int, perPage: Int): Single<List<UserSearchResult>> {
        return searchApi.searchUsers(
                query = userName,
                page = page,
                perPage = perPage
        ).map { response ->
            response.items
        }
    }
}