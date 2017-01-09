package com.develop.zuzik.repository.rx

import com.develop.zuzik.repository.core.Predicate
import com.develop.zuzik.repository.core.Repository
import rx.Observable.defer
import rx.Observable.just

/**
 * User: zuzik
 * Date: 1/8/17
 */
class RxRepository<Entity, Key>(private val repository: Repository<Entity, Key>) {

    fun create(entity: Entity) = defer { just(repository.create(entity)) }

    fun readWithKey(key: Key) = defer { just(repository.readWithKey(key)) }

    fun readWithPredicate(predicate: Predicate<Entity>) = defer { just(repository.readWithPredicate(predicate)) }

    fun readAll() = defer { just(repository.readAll()) }

    fun update(entity: Entity) = defer { just(repository.update(entity)) }

    fun delete(entity: Entity) = defer { just(repository.delete(entity)) }.map { Any() }

    fun deleteWithKey(key: Key) = defer { just(repository.deleteWithKey(key)) }.map { Any() }

    fun deleteWithPredicate(predicate: Predicate<Entity>) = defer { just(repository.deleteWithPredicate(predicate)) }.map { Any() }

}