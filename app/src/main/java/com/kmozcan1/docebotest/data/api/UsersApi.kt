package com.kmozcan1.docebotest.data.api

import com.kmozcan1.docebotest.data.apimodel.GetUserResponse
import com.kmozcan1.docebotest.data.apimodel.RepositoryApiModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Kadir Mert Özcan on 03-Jan-21.
 *
 * Retrofit Endpoint for "users/" Api calls to GitHub Api
 */
interface UsersApi {

    /**
     * Api Call to get a specific GitHub user profile
     */
    @Headers(
        "Accept: application/vnd.github.v3+json"
    )
    @GET("users/{username}")
    fun getUser(
        @Path("username") userName: String,
    ): Single<GetUserResponse>


    /**
     * Api Call to get repositories of a specific GitHub user
     */
    @Headers(
        "Accept: application/vnd.github.v3+json"
    )
    @GET("users/{username}/repos")
    fun getUserRepositories(
        @Path("username") userName: String,
        @Query("sort") sort: String = "",
        @Query("direction") direction: String = "",
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Single<List<RepositoryApiModel>>
}