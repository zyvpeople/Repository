package com.develop.zuzik.repository.core

import com.develop.zuzik.repository.core.exception.CreateEntityException
import com.develop.zuzik.repository.core.exception.DeleteEntityException
import com.develop.zuzik.repository.core.exception.ReadEntityException
import com.develop.zuzik.repository.core.exception.UpdateEntityException

/**
 * User: zuzik
 * Date: 1/7/17
 */
//TODO: add methods: deleteEntities, updateEntities, createEntities
interface Repository<Entity, in Key> {
    @Throws(CreateEntityException::class)
    fun create(entity: Entity): Entity

    @Throws(ReadEntityException::class)
    fun readWithKey(key: Key): Entity

    @Throws(ReadEntityException::class)
    fun readWithPredicate(predicate: Predicate<Entity>): List<Entity>

    @Throws(ReadEntityException::class)
    fun readAll(): List<Entity>

    @Throws(UpdateEntityException::class)
    fun update(entity: Entity): Entity

    @Throws(DeleteEntityException::class)
    fun delete(entity: Entity)

    @Throws(DeleteEntityException::class)
    fun deleteWithKey(key: Key)

    //TODO: investigate if subclasses should use concrete predicate in method's parameter
    @Throws(DeleteEntityException::class)
    fun deleteWithPredicate(predicate: Predicate<Entity>)
}