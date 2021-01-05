package com.kmozcan1.docebotest.data.apimodel

import com.squareup.moshi.Json

/**
 * Created by Kadir Mert Özcan on 05-Jan-21.
 *
 * Response data class for [com.kmozcan1.docebotest.data.api.UsersApi.getUserRepositories]
 */
data class GetUserRepositoriesResponse(
    @field:Json(name = "items") var items: List<RepositoryApiModel>
)
