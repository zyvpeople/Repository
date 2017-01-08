package com.develop.zuzik.repository.sample.application

import android.app.Application
import com.develop.zuzik.repository.sample.domain.filter.Filter
import com.develop.zuzik.repository.sample.domain.filter.FilterModel
import com.develop.zuzik.repository.sample.domain.users.UsersModel
import com.develop.zuzik.repository.sample.domain.users.UsersModelState

/**
 * User: zuzik
 * Date: 1/8/17
 */
class App : Application() {

    val filterModel = FilterModel(Filter(null, null))
    val usersModel = UsersModel(UsersModelState(emptyList()))

    override fun onCreate() {
        super.onCreate()
        usersModel.updateWithFilter(filterModel.stateObservable)
    }
}