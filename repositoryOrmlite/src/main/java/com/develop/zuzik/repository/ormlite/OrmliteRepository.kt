package com.develop.zuzik.repository.ormlite

import com.develop.zuzik.repository.core.Predicate
import com.develop.zuzik.repository.core.Repository
import com.develop.zuzik.repository.core.exception.CreateEntityException
import com.develop.zuzik.repository.core.exception.ReadEntityException
import com.j256.ormlite.dao.Dao

/**
 * User: zuzik
 * Date: 1/11/17
 */
open class OrmliteRepository<Entity, in Key, OrmliteEntity>(
        private val dao: Dao<OrmliteEntity, Key>,
        private val getKey: (Entity) -> Key?,
        private val entityToOrmliteEntity: (Entity) -> OrmliteEntity,
        private val ormliteEntityToEntity: (OrmliteEntity) -> Entity
) : Repository<Entity, Key> {

    @Throws(CreateEntityException::class)
    override fun create(entity: Entity): Entity {
        if (getKey(entity) != null) {
            throw CreateEntityException()
        }
        try {
            val ormliteEntity = entityToOrmliteEntity(entity)
            val createdOrmliteEntity = dao.createIfNotExists(ormliteEntity)
            return ormliteEntityToEntity(createdOrmliteEntity)
        } catch (e: Exception) {
            throw CreateEntityException()
        }
    }

    @Throws(ReadEntityException::class)
    override fun readWithKey(key: Key): Entity {
        try {
            val ormliteEntity = dao.queryForId(key) ?: throw ReadEntityException()
            return ormliteEntityToEntity(ormliteEntity)
        } catch (e: Exception) {
            throw ReadEntityException()
        }
    }

    @Throws(ReadEntityException::class)
    override fun readWithPredicate(predicate: Predicate<Entity>): List<Entity> {
        try {
            val ormlitePredicate = predicate as OrmlitePredicate<Entity, Key, OrmliteEntity>
            val queryBuilder = dao.queryBuilder()
            val where = ormlitePredicate.where(queryBuilder.where())
            return queryBuilder.query().map { ormliteEntityToEntity(it) }
        } catch (e: Exception) {
            throw ReadEntityException()
        }
    }

    override fun readAll(): List<Entity> {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(entity: Entity): Entity {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(entity: Entity) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteWithKey(key: Key) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteWithPredicate(predicate: Predicate<Entity>) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}