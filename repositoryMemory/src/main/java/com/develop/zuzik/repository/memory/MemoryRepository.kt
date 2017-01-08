package com.develop.zuzik.repository.memory

import com.develop.zuzik.repository.core.KeyGenerator
import com.develop.zuzik.repository.core.Predicate
import com.develop.zuzik.repository.core.Repository
import com.develop.zuzik.repository.core.exception.*

/**
 * User: zuzik
 * Date: 1/7/17
 */
open class MemoryRepository<Entity, in Key>(
        private val keyGenerator: KeyGenerator<Key>,
        private val getKey: (Entity) -> Key?,
        private val setKey: (Entity, Key) -> Entity,
        private val copy: (Entity) -> Entity) : Repository<Entity, Key> {

    private val entities = mutableListOf<Entity>()

    @Throws(CreateEntityException::class)
    override fun create(entity: Entity): Entity {
        if (getKey(entity) != null) {
            throw CreateEntityException()
        }
        try {
            val copyWithoutKey = copy(entity)
            val newKey = keyGenerator.generate().key
            val copyWithKey = setKey(copyWithoutKey, newKey)
            entities.add(copyWithKey)
            return copy(copyWithKey)
        } catch (e: GenerateKeyException) {
            throw CreateEntityException()
        }
    }

    @Throws(ReadEntityException::class)
    override fun readWithKey(key: Key): Entity {
        val entityWithKey = entities.firstOrNull { getKey(it) == key }
        return entityWithKey ?: throw ReadEntityException()
    }

    @Throws(ReadEntityException::class)
    override fun readWithPredicate(predicate: Predicate<Entity>): List<Entity> {
        try {
            val memoryPredicate = predicate as MemoryPredicate<Entity>
            return entities
                    .filter { memoryPredicate.satisfied(it) }
                    .map(copy)
        } catch (e: ClassCastException) {
            throw ReadEntityException()
        }
    }

    @Throws(ReadEntityException::class)
    override fun readAll(): List<Entity> = entities.map(copy)

    @Throws(UpdateEntityException::class)
    override fun update(entity: Entity): Entity {
        val key = getKey(entity) ?: throw UpdateEntityException()
        val index = entities.indexOfFirst { key == getKey(it) }
        if (index == -1) {
            throw UpdateEntityException()
        } else {
            entities.removeAt(index)
            val copy = copy(entity)
            entities.add(index, copy)
            return copy(copy)
        }
    }

    @Throws(DeleteEntityException::class)
    override fun delete(entity: Entity) {
        deleteWithKey(getKey(entity) ?: throw DeleteEntityException())
    }

    @Throws(DeleteEntityException::class)
    override fun deleteWithKey(key: Key) {
        val index = entities.indexOfFirst { key == getKey(it) }
        if (index == -1) {
            throw DeleteEntityException()
        } else {
            entities.removeAt(index)
        }
    }

    @Throws(DeleteEntityException::class)
    override fun deleteWithPredicate(predicate: Predicate<Entity>) {
        try {
            val memoryPredicate = predicate as MemoryPredicate<Entity>
            entities.removeAll { memoryPredicate.satisfied(it) }
        } catch (e: ClassCastException) {
            throw DeleteEntityException()
        }
    }
}