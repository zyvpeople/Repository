package com.develop.zuzik.repository.sample.datasource.repository.memory.user

import com.develop.zuzik.repository.memory.MemoryPredicate
import com.develop.zuzik.repository.sample.domain.entity.User
import com.develop.zuzik.repository.sample.domain.filter.Filter

/**
 * User: zuzik
 * Date: 1/9/17
 */
class UserFilterMemoryPredicate(private val filter: Filter) : MemoryPredicate<User> {
    override fun satisfied(entity: User): Boolean {
        val satisfiedName = filter.name == null || entity.name.contains(filter.name, true)
        val satisfiedAge = filter.age == null || filter.age == entity.age
        return satisfiedName && satisfiedAge
    }
}