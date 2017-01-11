package com.develop.zuzik.repository.sample.datasource.repository.realm.user

import com.develop.zuzik.repository.realm.RealmPredicate
import com.develop.zuzik.repository.sample.domain.entity.User
import com.develop.zuzik.repository.sample.domain.filter.Filter
import io.realm.Case
import io.realm.RealmQuery

/**
 * User: zuzik
 * Date: 1/11/17
 */
class UserFilterRealmPredicate(private val filter: Filter) : RealmPredicate<User, UserRealmObject> {
    override fun where(query: RealmQuery<UserRealmObject>): RealmQuery<UserRealmObject> {
        if (filter.name != null) {
            query.contains("name", filter.name, Case.INSENSITIVE)
        }
        if (filter.age != null) {
            query.equalTo("age", filter.age)
        }
        return query
    }
}