package com.kmozcan1.docebotest.data.repository

import com.kmozcan1.docebotest.data.api.UsersApi
import com.kmozcan1.docebotest.data.apimodel.GetUserResponse
import com.kmozcan1.docebotest.data.apimodel.RepositoryApiModel
import com.kmozcan1.docebotest.domain.repository.UsersRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 03-Jan-21.
 */
class UsersRepositoryImpl @Inject constructor(private val usersApi: UsersApi): UsersRepository {
    override fun getUser(userName: String): Single<GetUserResponse> {
        return usersApi.getUser(userName)
    }

    override fun getUserRepositories(
            userName: String,
            sort: String,
            direction: String,
            page: Int,
            perPage: Int
    ): Single<List<RepositoryApiModel>> {
        return usersApi.getUserRepositories(userName, sort, direction, page, perPage)
    }


}