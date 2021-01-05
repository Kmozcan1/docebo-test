package com.kmozcan1.docebotest.domain.mapper

import com.kmozcan1.docebotest.data.apimodel.RepositoryApiModel
import com.kmozcan1.docebotest.domain.model.RepositoryModel
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 05-Jan-21.
 *
 * Maps [RepositoryApiModel] response object to [RepositoryModel] domain model
 */
class RepositoryMapper @Inject constructor(): Mapper<RepositoryApiModel, RepositoryModel> {
    override fun map(repositoryModel: RepositoryApiModel) =
        repositoryModel.run {
            RepositoryModel(name = name, starCount = stargazersCount)
        }
}