package com.develop.zuzik.repository.sample.datasource.repository.ormlite.user

import com.develop.zuzik.repository.ormlite.OrmliteRepository
import com.develop.zuzik.repository.sample.datasource.repository.UserRepository
import com.develop.zuzik.repository.sample.domain.entity.User
import com.j256.ormlite.dao.Dao

/**
 * User: zuzik
 * Date: 1/11/17
 */
class UserOrmliteRepository(dao: Dao<UserOrmliteEntity, Long>) : OrmliteRepository<User, Long, UserOrmliteEntity>(
        dao,
        User::id,
        {
            val userOrmliteEntity = UserOrmliteEntity()
            userOrmliteEntity.id = it.id ?: 0
            userOrmliteEntity.name = it.name
            userOrmliteEntity.age = it.age
            userOrmliteEntity
        },
        {
            User(
                    id = if (it.id != 0L) it.id else null,
                    name = it.name,
                    age = it.age)
        },
        {
            it.copy()
        }), UserRepository