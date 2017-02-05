package com.develop.zuzik.repository.ormlite

import com.develop.zuzik.repository.core.Predicate
import com.develop.zuzik.repository.core.Repository
import com.develop.zuzik.repository.core.exception.CreateEntityException
import com.develop.zuzik.repository.core.exception.DeleteEntityException
import com.develop.zuzik.repository.core.exception.ReadEntityException
import com.develop.zuzik.repository.core.exception.UpdateEntityException
import com.j256.ormlite.dao.Dao

/**
 * User: zuzik
 * Date: 1/11/17
 */
open class OrmliteRepository<Entity, in Key, OrmliteEntity>(
        private val dao: Dao<OrmliteEntity, Key>,
        private val getKey: (Entity) -> Key?,
        private val entityToOrmliteEntity: (Entity) -> OrmliteEntity,
        private val ormliteEntityToEntity: (OrmliteEntity) -> Entity,
        private val copy: (Entity) -> Entity
) : Repository<Entity, Key> {

    @Throws(CreateEntityException::class)
    override fun create(entity: Entity): Entity {
        if (getKey(entity) != null) {
            throw CreateEntityException.Factory.entityDoesNotHaveKey(entity)
        }
        try {
            val ormliteEntity = entityToOrmliteEntity(entity)
            val createdOrmliteEntity = dao.createIfNotExists(ormliteEntity)
            return ormliteEntityToEntity(createdOrmliteEntity)
        } catch (e: Exception) {
            throw CreateEntityException(e.message)
        }
    }

    @Throws(ReadEntityException::class)
    override fun readWithKey(key: Key): Entity {
        try {
            val ormliteEntity = dao.queryForId(key) ?: throw ReadEntityException.Factory.entityWithKeyDoesNotExist(key)
            return ormliteEntityToEntity(ormliteEntity)
        } catch (e: Exception) {
            throw ReadEntityException(e.message)
        }
    }

    @Throws(ReadEntityException::class)
    override fun readWithPredicate(predicate: Predicate<Entity>): List<Entity> {
        try {
            val ormlitePredicate = predicate as OrmlitePredicate<Entity, Key, OrmliteEntity>
            val queryBuilder = dao.queryBuilder()
            queryBuilder.setWhere(ormlitePredicate.where(queryBuilder.where()))
            return queryBuilder.query().map { ormliteEntityToEntity(it) }
        } catch (e: Exception) {
            throw ReadEntityException(e.message)
        }
    }

    @Throws(ReadEntityException::class)
    override fun readAll(): List<Entity> {
        try {
            return dao.queryForAll().map { ormliteEntityToEntity(it) }
        } catch (e: Exception) {
            throw ReadEntityException(e.message)
        }
    }

    @Throws(UpdateEntityException::class)
    override fun update(entity: Entity): Entity {
        try {
            if (getKey(entity) == null) {
                throw UpdateEntityException.Factory.entityDoesNotHaveKey(entity)
            }
            val updatedEntitiesCount = dao.update(entityToOrmliteEntity(entity))
            return if (updatedEntitiesCount == 1) {
                copy(entity)
            } else {
                throw UpdateEntityException.Factory.entityIsNotUpdated(entity)
            }
        } catch (e: Exception) {
            throw UpdateEntityException(e.message)
        }
    }

    @Throws(DeleteEntityException::class)
    override fun delete(entity: Entity) {
        deleteWithKey(getKey(entity) ?: throw DeleteEntityException.Factory.entityDoesNotHaveKey(entity))
    }

    @Throws(DeleteEntityException::class)
    override fun deleteWithKey(key: Key) {
        try {
            val deletedEntitiesCount = dao.deleteById(key)
            if (deletedEntitiesCount == 0) {
                throw DeleteEntityException.Factory.entityWithKeyDoesNotExist(key)
            }
        } catch (e: Exception) {
            throw DeleteEntityException(e.message)
        }
    }

    @Throws(DeleteEntityException::class)
    override fun deleteWithPredicate(predicate: Predicate<Entity>) {
        try {
            val ormlitePredicate = predicate as OrmlitePredicate<Entity, Key, OrmliteEntity>
            val deleteBuilder = dao.deleteBuilder()
            deleteBuilder.setWhere(ormlitePredicate.where(deleteBuilder.where()))
            deleteBuilder.delete()
        } catch (e: Exception) {
            throw DeleteEntityException(e.message)
        }
    }
}