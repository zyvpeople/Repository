package com.develop.zuzik.repository.realm

import com.develop.zuzik.repository.core.Predicate
import com.develop.zuzik.repository.core.Repository
import com.develop.zuzik.repository.core.exception.CreateEntityException
import com.develop.zuzik.repository.core.exception.ReadEntityException
import io.realm.Realm
import io.realm.RealmModel

/**
 * User: zuzik
 * Date: 1/11/17
 */
open class RealmRepository<Entity, in Key, RealmEntity : RealmModel>(
        private val realm: Realm,
        private val realmEntityClass: Class<RealmEntity>,
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

    override fun readWithKey(key: Key): Entity {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Throws(ReadEntityException::class)
    override fun readWithPredicate(predicate: Predicate<Entity>): List<Entity> {
        try {
            val realmPredicate = predicate as RealmPredicate<Entity, RealmEntity>
            val result = realmPredicate.where(realm.where(realmEntityClass)).findAll()
            return result.map { realmEntityToEntity(it) }
        } catch (e: ClassCastException) {
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