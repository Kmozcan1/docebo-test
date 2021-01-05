package com.kmozcan1.docebotest.data.api

import com.kmozcan1.docebotest.data.apimodel.SearchUserResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by Kadir Mert Özcan on 28-Dec-20.
 *
 * Retrofit Endpoint for "search/" Api calls to GitHub Api
 */

@JvmSuppressWildcards
interface SearchApi {
    @Headers(
        "Accept: application/vnd.github.v3+json"
    )
    @GET("search/users")
    fun searchUsers(
        @Query("q") query: String,
        @Query("sort") sort: String? = null,
        @Query("order") order: String? = null,
        @Query("per_page") perPage: Int? = null,
        @Query("page") page: Int? = null
    ): Single<SearchUserResponse>
}