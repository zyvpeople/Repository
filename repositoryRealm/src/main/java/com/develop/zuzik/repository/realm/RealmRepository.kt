package com.develop.zuzik.repository.realm

import com.develop.zuzik.repository.core.Predicate
import com.develop.zuzik.repository.core.Repository
import com.develop.zuzik.repository.core.exception.CreateEntityException
import com.develop.zuzik.repository.core.exception.DeleteEntityException
import com.develop.zuzik.repository.core.exception.ReadEntityException
import com.develop.zuzik.repository.core.exception.UpdateEntityException
import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmObject
import io.realm.RealmQuery

/**
 * User: zuzik
 * Date: 1/11/17
 */
open class RealmRepository<Entity, in Key, RealmEntity : RealmModel>(
        private val realm: Realm,
        private val realmEntityClass: Class<RealmEntity>,
        private val keyWhereQuery: (RealmQuery<RealmEntity>, key: Key) -> RealmQuery<RealmEntity>,
        private val keyGenerator: KeyGenerator<Key, RealmEntity>,
        private val getKey: (Entity) -> Key?,
        private val entityToRealmEntity: (Entity) -> RealmEntity,
        private val realmEntityToEntity: (RealmEntity) -> Entity,
        private val setKey: (Entity, Key) -> Entity,
        private val copy: (Entity) -> Entity) : Repository<Entity, Key> {

    @Throws(CreateEntityException::class)
    override fun create(entity: Entity): Entity {
        if (getKey(entity) != null) {
            throw CreateEntityException()
        }
        try {
            val copyWithoutKey = copy(entity)
            val newKey = keyGenerator.generate(realm.where(realmEntityClass))
            val copyWithKey = setKey(copyWithoutKey, newKey)
            realm.executeTransaction {
                val realmEntity = entityToRealmEntity(copyWithKey)
                it.copyToRealm(realmEntity)
            }
            return copyWithKey
        } catch (e: Exception) {
            throw CreateEntityException()
        }
    }

    @Throws(ReadEntityException::class)
    override fun readWithKey(key: Key): Entity {
        try {
            val entity = keyWhereQuery(realm.where(realmEntityClass), key).findFirst()
            return if (entity != null) {
                realmEntityToEntity(entity)
            } else {
                throw ReadEntityException()
            }
        } catch (e: Exception) {
            throw ReadEntityException()
        }
    }

    @Throws(ReadEntityException::class)
    override fun readWithPredicate(predicate: Predicate<Entity>): List<Entity> {
        try {
            val realmPredicate = predicate as RealmPredicate<Entity, RealmEntity>
            val result = realmPredicate.where(realm.where(realmEntityClass)).findAll()
            return result.map { realmEntityToEntity(it) }
        } catch (e: Exception) {
            throw ReadEntityException()
        }
    }

    @Throws(ReadEntityException::class)
    override fun readAll(): List<Entity> {
        try {
            return realm.where(realmEntityClass).findAll().map { realmEntityToEntity(it) }
        } catch (e: Exception) {
            throw ReadEntityException()
        }
    }

    @Throws(UpdateEntityException::class)
    override fun update(entity: Entity): Entity {
        try {
            //TODO: check if id exist and entity with id exist
            val realmEntity = realm.copyToRealmOrUpdate(entityToRealmEntity(entity))
            return realmEntityToEntity(realmEntity)
        } catch (e: Exception) {
            throw UpdateEntityException()
        }
    }

    @Throws(DeleteEntityException::class)
    override fun delete(entity: Entity) {
        deleteWithKey(getKey(entity) ?: throw DeleteEntityException())
    }

    @Throws(DeleteEntityException::class)
    override fun deleteWithKey(key: Key) {
        try {
            val realmEntity = keyWhereQuery(realm.where(realmEntityClass), key).findFirst() ?: throw DeleteEntityException()
            realm.executeTransaction { RealmObject.deleteFromRealm(realmEntity) }
        } catch (e: Exception) {
            throw DeleteEntityException()
        }
    }

    @Throws(DeleteEntityException::class)
    override fun deleteWithPredicate(predicate: Predicate<Entity>) {
        try {
            val realmPredicate = predicate as RealmPredicate<Entity, RealmEntity>
            val entities = realmPredicate.where(realm.where(realmEntityClass)).findAll()
            realm.executeTransaction { entities.deleteAllFromRealm() }
        } catch (e: Exception) {
            throw DeleteEntityException()
        }
    }
}