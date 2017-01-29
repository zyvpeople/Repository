package com.develop.zuzik.repository.sample.datasource.repository

import com.develop.zuzik.repository.asserts.RepositoryAssertStrategy
import com.develop.zuzik.repository.core.Predicate
import com.develop.zuzik.repository.core.Repository
import com.develop.zuzik.repository.sample.domain.entity.User

/**
 * User: zuzik
 * Date: 1/13/17
 */
class UserRepositoryAssertStrategy(private val createRepository: () -> Repository<User, Long>,
                                   private val clearRepo: () -> Unit,
                                   private val entityWithKeyPredicateFactory: (key: Long) -> Predicate<User>) : RepositoryAssertStrategy<User, Long> {

    override fun clearRepository() {
        clearRepo()
    }

    override fun repository() = createRepository()

    override fun createEntity() = User(
            id = null,
            name = "zuzik",
            age = 23)

    override fun hasKey(entity: User) = (entity.id ?: 0) != 0L

    override fun key1() = 1L

    override fun key2() = 2L

    override fun notExistedKey() = 100500L

    override fun getKey(entity: User) = entity.id ?: 0

    override fun setKey(entity: User, key: Long) = entity.copy(id = key)

    override fun entityWithKeyPredicate(key: Long): Predicate<User> = entityWithKeyPredicateFactory(key)

}