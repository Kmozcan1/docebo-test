package com.kmozcan1.docebotest.data.api

import com.kmozcan1.docebotest.data.apimodel.GetUserResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

/**
 * Created by Kadir Mert Özcan on 03-Jan-21.
 */
interface UsersApi {
    @Headers(
        "Accept: application/vnd.github.v3+json"
    )
    @GET("users/{username}")
    fun getUser(
        @Path("username") userName: String,
    ): Single<GetUserResponse>
}