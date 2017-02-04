package com.develop.zuzik.repository.asserts

import com.develop.zuzik.repository.core.Predicate
import com.develop.zuzik.repository.core.Repository

/**
 * User: zuzik
 * Date: 1/13/17
 */
interface RepositoryAssertStrategy<Entity, Key> {

    fun createRepository(): Repository<Entity, Key>
    fun clearRepository()
    fun createEntity(): Entity
    fun hasKey(entity: Entity): Boolean
    fun key1(): Key
    fun key2(): Key
    fun getKey(entity: Entity): Key
    fun setKey(entity: Entity, key: Key): Entity
    fun entityWithKeyPredicate(key: Key): Predicate<Entity>
    fun updateProperties(entity: Entity): Entity
}