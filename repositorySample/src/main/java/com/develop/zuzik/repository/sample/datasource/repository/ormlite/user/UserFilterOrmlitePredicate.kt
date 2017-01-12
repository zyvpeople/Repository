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
    override fun where(where: Where<UserRealmObject, Long>): Where<UserRealmObject, Long>? {
        val hasName = filter.name != null
        val hasAge = filter.age != null

        if (hasName) {
            where.like("name", "%${filter.name}%")
        }
        if (hasAge) {
            if (hasName) {
                where.and()
            }
            where.eq("age", filter.age)
        }

        return if (hasName || hasAge) where else null
    }
}