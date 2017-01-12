package com.develop.zuzik.repository.sample.datasource.repository.activeandroid.user

import com.activeandroid.query.From
import com.develop.zuzik.repository.activeandroid.ActiveAndroidPredicate
import com.develop.zuzik.repository.sample.domain.entity.User
import com.develop.zuzik.repository.sample.domain.filter.Filter

/**
 * User: zuzik
 * Date: 1/12/17
 */
class UserFilterActiveAndroidPredicate(private val filter: Filter) : ActiveAndroidPredicate<User> {
    override fun query(from: From): From {
        if (filter.name != null) {
            from.where("name like ?", "%${filter.name}%")
        }
        if (filter.age != null) {
            from.where("age = ?", filter.age)
        }
        return from
    }
}