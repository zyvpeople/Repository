package com.develop.zuzik.repository.sample.datasource.repository.realm.user

import com.develop.zuzik.repository.realm.RealmRepository
import com.develop.zuzik.repository.realm.key_generator.LongKeyGenerator
import com.develop.zuzik.repository.sample.datasource.repository.UserRepository
import com.develop.zuzik.repository.sample.domain.entity.User
import io.realm.Realm

/**
 * User: zuzik
 * Date: 1/11/17
 */
class UserRealmRepository(realm: Realm) : RealmRepository<User, Long, UserRealmObject>(
        realm,
        UserRealmObject::class.java,
        { query, id -> query.equalTo("id", id) },
        LongKeyGenerator <UserRealmObject>("id"),
        User::id,
        {
            val userRealmObject = UserRealmObject()
            userRealmObject.id = it.id ?: 0
            userRealmObject.name = it.name
            userRealmObject.age = it.age
            userRealmObject
        },
        {
            User(
                    id = if (it.id != 0L) it.id else null,
                    name = it.name,
                    age = it.age)
        },
        { user, id -> user.copy(id = id) },
        { it.copy() }), UserRepository