package com.kmozcan1.docebotest.domain.model

/**
 * Created by Kadir Mert Özcan on 03-Jan-21.
 */
data class UserSearchResult(
    val userList: List<UserListItem>,
    val finalPage: Boolean)
