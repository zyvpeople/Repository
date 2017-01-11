package com.develop.zuzik.repository.sample.datasource.repository.ormlite.user

import com.develop.zuzik.repository.ormlite.OrmlitePredicate
import com.develop.zuzik.repository.sample.datasource.repository.realm.user.UserRealmObject
import com.develop.zuzik.repository.sample.domain.entity.User
import com.develop.zuzik.repository.sample.domain.filter.Filter
import com.j256.ormlite.stmt.Where

/**
 * User: zuzik
 * Date: 1/11/17
 */
class UserFilterOrmlitePredicate(private val filter: Filter) : OrmlitePredicate<User, Long, UserRealmObject> {
    override fun where(where: Where<UserRealmObject, Long>): Where<UserRealmObject, Long> {
        if (filter.name != null) {
            where.like("name", "%${filter.name}%")
        }
        if (filter.age != null) {
            if (filter.name != null) {
                where.and()
            }
            where.eq("age", filter.age)
        }
        return where
    }
}