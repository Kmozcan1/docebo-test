package com.kmozcan1.docebotest.domain.model

/**
 * Created by Kadir Mert Özcan on 03-Jan-21.
 *
 * Domain model for GitHub user profile
 */
data class UserProfileModel(
    val userName: String,
    val fullName: String,
    val email: String,
    val profileUrl: String,
    val avatarUrl: String)
