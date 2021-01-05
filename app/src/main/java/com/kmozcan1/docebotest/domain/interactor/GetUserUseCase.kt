package com.kmozcan1.docebotest.domain.interactor

import com.kmozcan1.docebotest.domain.interactor.base.SingleUseCase
import com.kmozcan1.docebotest.domain.mapper.UserMapper
import com.kmozcan1.docebotest.domain.model.UserProfileModel
import com.kmozcan1.docebotest.domain.repository.UsersRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 03-Jan-21.
 */
class GetUserUseCase @Inject constructor (
    private val usersRepository: UsersRepository,
    private val userMapper: UserMapper
): SingleUseCase<UserProfileModel, GetUserUseCase.Params>() {
    data class Params(val userName: String)

    override fun buildObservable(params: Params?): Single<UserProfileModel> {
        return usersRepository.getUser(params!!.userName).map { response ->
            userMapper.map(response)
        }
    }
}