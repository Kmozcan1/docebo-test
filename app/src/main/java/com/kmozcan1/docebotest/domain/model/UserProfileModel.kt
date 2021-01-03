package com.kmozcan1.docebotest.domain.model

/**
 * Created by Kadir Mert Özcan on 03-Jan-21.
 */
data class UserProfileModel(
    val userName: String,
    val fullName: String,
    val email: String,
    val profileUrl: String,
    val avatarUrl: String)
