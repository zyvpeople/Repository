package com.develop.zuzik.repository.sample.datasource.repository.activeandroid.user

import com.develop.zuzik.repository.activeandroid.ActiveAndroidRepository
import com.develop.zuzik.repository.sample.datasource.repository.UserRepository
import com.develop.zuzik.repository.sample.domain.entity.User

/**
 * User: zuzik
 * Date: 1/12/17
 */
class UserActiveAndroidRepository : ActiveAndroidRepository<User, UserActiveAndroidModel>(
        UserActiveAndroidModel::class.java,
        {
            val userActiveAndroidModel = UserActiveAndroidModel()
            userActiveAndroidModel.name = it.name
            userActiveAndroidModel.age = it.age
            userActiveAndroidModel
        },
        {
            User(
                    id = if (it.id != 0L) it.id else null,
                    name = it.name,
                    age = it.age)
        },
        { it.copy() },
        User::id,
        { user, id -> user.copy(id = id) },
        { userActiveAndroidModel, user ->
            userActiveAndroidModel.name = user.name
            userActiveAndroidModel.age = user.age
            userActiveAndroidModel
        }
), UserRepository