package com.kmozcan1.docebotest.domain.repository

import com.kmozcan1.docebotest.data.apimodel.GetUserResponse
import com.kmozcan1.docebotest.data.apimodel.UserSearchResult
import io.reactivex.rxjava3.core.Single

/**
 * Created by Kadir Mert Özcan on 03-Jan-21.
 */
interface UsersRepository {
    fun getUser(userName: String): Single<GetUserResponse>
}