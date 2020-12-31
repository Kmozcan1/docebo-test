package com.kmozcan1.docebotest.domain.mapper

/**
 * Created by Kadir Mert Özcan on 31-Dec-20.
 *
 * Interface for Mapper classes
 */
interface Mapper<in RepositoryModel, out DomainModel> {
    fun map(repositoryModel: RepositoryModel): DomainModel
}