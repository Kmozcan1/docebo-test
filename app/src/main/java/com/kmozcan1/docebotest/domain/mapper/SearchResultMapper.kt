package com.kmozcan1.docebotest.domain.mapper

import com.kmozcan1.docebotest.data.apimodel.UserSearchResult
import com.kmozcan1.docebotest.domain.model.UserListItem
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 31-Dec-20.
 *
 * Maps [UserSearchResult] response object to [UserListItem] domain model
 */
class SearchResultMapper @Inject constructor(): Mapper<UserSearchResult, UserListItem> {
    override fun map(repositoryModel: UserSearchResult) =
        repositoryModel.run {
            UserListItem(id = id, userName = login, avatarUrl = avatarUrl)
        }
}