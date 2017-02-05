package com.develop.zuzik.repository.rx2

import com.develop.zuzik.repository.core.Predicate
import com.develop.zuzik.repository.core.Repository
import io.reactivex.Completable
import io.reactivex.Single

/**
 * User: zuzik
 * Date: 2/5/17
 */
class RxRepository<Entity, in Key>(private val repository: Repository<Entity, Key>) {

    fun create(entity: Entity): Single<Entity> = Single.defer { Single.just(repository.create(entity)) }

    fun readWithKey(key: Key): Single<Entity> = Single.defer { Single.just(repository.readWithKey(key)) }

    fun readWithPredicate(predicate: Predicate<Entity>): Single<List<Entity>> = Single.defer { Single.just(repository.readWithPredicate(predicate)) }

    fun readAll(): Single<List<Entity>> = Single.defer { Single.just(repository.readAll()) }

    fun update(entity: Entity): Single<Entity> = Single.defer { Single.just(repository.update(entity)) }

    fun delete(entity: Entity): Completable = Completable.fromCallable { repository.delete(entity) }

    fun deleteWithKey(key: Key): Completable = Completable.fromCallable { repository.deleteWithKey(key) }

    fun deleteWithPredicate(predicate: Predicate<Entity>): Completable = Completable.fromCallable { repository.deleteWithPredicate(predicate) }
}