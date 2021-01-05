package com.kmozcan1.docebotest.domain.mapper

import com.kmozcan1.docebotest.data.apimodel.UserSearchApiModel
import com.kmozcan1.docebotest.domain.model.UserSearchItemModel
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 31-Dec-20.
 *
 * Maps [UserSearchApiModel] response object to [UserSearchItemModel] domain model
 */
class SearchResultMapper @Inject constructor(): Mapper<UserSearchApiModel, UserSearchItemModel> {
    override fun map(repositoryModel: UserSearchApiModel) =
        repositoryModel.run {
            UserSearchItemModel(id = id, userName = login, avatarUrl = avatarUrl)
        }
}