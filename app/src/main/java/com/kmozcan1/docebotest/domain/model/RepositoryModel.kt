package com.kmozcan1.docebotest.domain.model

import com.squareup.moshi.Json

/**
 * Created by Kadir Mert Özcan on 05-Jan-21.
 *
 * Domain model for repository
 */
data class RepositoryModel(var name: String, var starCount: Int)
