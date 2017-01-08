package com.develop.zuzik.repository.sample.application

import android.app.Application
import com.develop.zuzik.repository.sample.datasource.repository.user.UserMemoryRepository
import com.develop.zuzik.repository.sample.domain.filter.Filter
import com.develop.zuzik.repository.sample.domain.filter.FilterModel
import com.develop.zuzik.repository.sample.domain.users.UsersModel
import com.develop.zuzik.repository.sample.domain.users.UsersModelState

/**
 * User: zuzik
 * Date: 1/8/17
 */
class App : Application() {

    val filter = Filter(null, null)
    val userRepository = UserMemoryRepository()
    val filterModel = FilterModel(filter)
    val usersModel = UsersModel(UsersModelState(emptyList(), filter), userRepository)

    override fun onCreate() {
        super.onCreate()
        usersModel.updateWithFilterIntent(filterModel.stateObservable)
    }
}