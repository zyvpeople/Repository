package com.develop.zuzik.repository.sample.datasource.repository.memory.user

import com.develop.zuzik.repository.core.Predicate
import com.develop.zuzik.repository.core.PredicateParameterFactory
import com.develop.zuzik.repository.sample.domain.entity.User
import com.develop.zuzik.repository.sample.domain.filter.Filter

/**
 * User: zuzik
 * Date: 1/9/17
 */
class UserFilterMemoryPredicateParameterFactory : PredicateParameterFactory<User, Filter> {
    override fun create(parameter: Filter): Predicate<User> = UserFilterMemoryPredicate(parameter)
}