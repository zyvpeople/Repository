package com.develop.zuzik.repository.sample.datasource.repository.realm.user

import com.develop.zuzik.repository.core.Predicate
import com.develop.zuzik.repository.core.PredicateParameterFactory
import com.develop.zuzik.repository.sample.domain.entity.User
import com.develop.zuzik.repository.sample.domain.filter.Filter

/**
 * User: zuzik
 * Date: 1/11/17
 */
class UserFilterRealmPredicateParameterFactory : PredicateParameterFactory<User, Filter> {
    override fun create(parameter: Filter): Predicate<User> = UserFilterRealmPredicate(parameter)
}