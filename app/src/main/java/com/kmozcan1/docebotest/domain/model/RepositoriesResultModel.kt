package com.kmozcan1.docebotest.domain.model

/**
 * Created by Kadir Mert Özcan on 03-Jan-21.
 *
 * Domain model that includes the result list and whether the final page has been reached or not
 */
data class RepositoriesResultModel(
        val repositoryList: List<RepositoryModel>,
        val finalPage: Boolean)