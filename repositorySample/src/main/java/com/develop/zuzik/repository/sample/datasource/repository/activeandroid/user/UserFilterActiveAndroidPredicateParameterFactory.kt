package com.develop.zuzik.repository.sample.datasource.repository.activeandroid.user

import com.develop.zuzik.repository.core.PredicateParameterFactory
import com.develop.zuzik.repository.sample.domain.entity.User
import com.develop.zuzik.repository.sample.domain.filter.Filter

/**
 * User: zuzik
 * Date: 1/12/17
 */
class UserFilterActiveAndroidPredicateParameterFactory : PredicateParameterFactory<User, Filter> {
    override fun create(parameter: Filter) = UserFilterActiveAndroidPredicate(parameter)
}