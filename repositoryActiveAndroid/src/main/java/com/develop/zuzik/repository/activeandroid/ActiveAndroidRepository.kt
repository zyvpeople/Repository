package com.develop.zuzik.repository.activeandroid

import com.activeandroid.Model
import com.activeandroid.query.Delete
import com.activeandroid.query.Select
import com.develop.zuzik.repository.core.Predicate
import com.develop.zuzik.repository.core.Repository
import com.develop.zuzik.repository.core.exception.CreateEntityException
import com.develop.zuzik.repository.core.exception.DeleteEntityException
import com.develop.zuzik.repository.core.exception.ReadEntityException
import com.develop.zuzik.repository.core.exception.UpdateEntityException

/**
 * User: zuzik
 * Date: 1/12/17
 */
//TODO: throw error with message
open class ActiveAndroidRepository<Entity, ActiveAndroidEntity : Model>(
        private val activeAndroidEntityClass: Class<ActiveAndroidEntity>,
        private val entityToActiveAndroidEntity: (Entity) -> ActiveAndroidEntity,
        private val activeAndroidEntityToEntity: (ActiveAndroidEntity) -> Entity,
        private val copy: (Entity) -> Entity,
        private val getKey: (Entity) -> Long?,
        private val setKey: (Entity, Long) -> Entity,
        private val fillActiveAndroidEntity: (ActiveAndroidEntity, Entity) -> ActiveAndroidEntity
) : Repository<Entity, Long> {

    @Throws(CreateEntityException::class)
    override fun create(entity: Entity): Entity {
        try {
            val activeAndroidEntity = entityToActiveAndroidEntity(entity)
            val id = activeAndroidEntity.save()
            val copy = copy(entity)
            val copyWithId = setKey(copy, id)
            return copyWithId
        } catch (e: Exception) {
            throw CreateEntityException()
        }
    }

    @Throws(ReadEntityException::class)
    override fun readWithKey(key: Long): Entity {
        try {
            val activeAndroidEntity = Model.load(activeAndroidEntityClass, key)
            return activeAndroidEntityToEntity(activeAndroidEntity)
        } catch (e: Exception) {
            throw ReadEntityException()
        }
    }

    @Throws(ReadEntityException::class)
    override fun readWithPredicate(predicate: Predicate<Entity>): List<Entity> {
        try {
            val activeAndroidPredicate = predicate as ActiveAndroidPredicate<Entity>
            return activeAndroidPredicate
                    .query(Select().from(activeAndroidEntityClass))
                    .execute<ActiveAndroidEntity>()
                    .map(activeAndroidEntityToEntity)
        } catch (e: Exception) {
            throw ReadEntityException()
        }
    }

    @Throws(ReadEntityException::class)
    override fun readAll(): List<Entity> {
        try {
            return Select().from(activeAndroidEntityClass).execute<ActiveAndroidEntity>().map(activeAndroidEntityToEntity)
        } catch (e: Exception) {
            throw ReadEntityException()
        }
    }

    @Throws(UpdateEntityException::class)
    override fun update(entity: Entity): Entity {
        try {
            val activeAndroidEntity = Model.load(activeAndroidEntityClass, getKey(entity)!!)
            val modifiedActiveAndroidEntity = fillActiveAndroidEntity(activeAndroidEntity, entity)
            modifiedActiveAndroidEntity.save()
            return copy(entity)
        } catch (e: Exception) {
            throw UpdateEntityException()
        }
    }

    @Throws(DeleteEntityException::class)
    override fun delete(entity: Entity) {
        deleteWithKey(getKey(entity)!!)
    }

    @Throws(DeleteEntityException::class)
    override fun deleteWithKey(key: Long) {
        try {
            val activeAndroidEntity = Model.load(activeAndroidEntityClass, key)
            activeAndroidEntity.delete()
        } catch (e: Exception) {
            throw DeleteEntityException()
        }
    }

    @Throws(DeleteEntityException::class)
    override fun deleteWithPredicate(predicate: Predicate<Entity>) {
        try {
            val activeAndroidPredicate = predicate as ActiveAndroidPredicate<Entity>
            activeAndroidPredicate
                    .query(Delete().from(activeAndroidEntityClass))
                    .execute<ActiveAndroidEntity>()
        } catch (e: Exception) {
            throw ReadEntityException()
        }
    }
}