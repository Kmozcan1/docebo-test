package com.kmozcan1.docebotest.domain.repository

import com.kmozcan1.docebotest.data.apimodel.UserSearchResult
import io.reactivex.rxjava3.core.Single

/**
 * Created by Kadir Mert Özcan on 30-Dec-20.
 *
 * Repository interface to be used by an interactor (use case class)
 */
interface SearchRepository {
    fun searchUser(userName: String, page: Int, perPage: Int): Single<List<UserSearchResult>>
}