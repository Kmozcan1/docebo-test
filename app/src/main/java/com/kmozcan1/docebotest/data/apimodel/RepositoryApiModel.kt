package com.kmozcan1.docebotest.data.apimodel

import com.squareup.moshi.Json

/**
 * Created by Kadir Mert Özcan on 05-Jan-21.
 *
 * Data class that represents a repository, used in [GetUserRepositoriesResponse]
 */
data class RepositoryApiModel(
    @Json(name = "id") @field:Json(name = "id") var id: Int,
    @Json(name = "node_id") @field:Json(name = "node_id") var nodeId: String,
    @Json(name = "name") @field:Json(name = "name") var name: String,
    @Json(name = "stargazers_count") @field:Json(name = "stargazers_count") var stargazersCount: Int
)
