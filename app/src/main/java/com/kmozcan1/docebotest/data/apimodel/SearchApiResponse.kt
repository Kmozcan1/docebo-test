package com.kmozcan1.docebotest.data.apimodel

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Created by Kadir Mert Özcan on 28-Dec-20.
 */
@JsonClass(generateAdapter = true)
data class SearchApiResponse(
    @field:Json(name = "total_count") var totalCount: Int? = null,
    @field:Json(name = "incomplete_results") var incompleteResults: Boolean? = null,
    @field:Json(name = "items") var items: List<UserSearchResult>? = null
)
