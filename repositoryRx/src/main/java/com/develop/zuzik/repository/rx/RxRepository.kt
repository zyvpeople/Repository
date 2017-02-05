package com.develop.zuzik.repository.rx

import com.develop.zuzik.repository.core.Predicate
import com.develop.zuzik.repository.core.Repository
import rx.Observable
import rx.Observable.defer
import rx.Observable.just

/**
 * User: zuzik
 * Date: 1/8/17
 */
class RxRepository<Entity, in Key>(private val repository: Repository<Entity, Key>) {

    fun create(entity: Entity): Observable<Entity> = defer { just(repository.create(entity)) }

    fun readWithKey(key: Key): Observable<Entity> = defer { just(repository.readWithKey(key)) }

    fun readWithPredicate(predicate: Predicate<Entity>): Observable<List<Entity>> = defer { just(repository.readWithPredicate(predicate)) }

    fun readAll(): Observable<List<Entity>> = defer { just(repository.readAll()) }

    fun update(entity: Entity): Observable<Entity> = defer { just(repository.update(entity)) }

    fun delete(entity: Entity): Observable<Boolean> = defer { just(repository.delete(entity)) }.map { true }

    fun deleteWithKey(key: Key): Observable<Boolean> = defer { just(repository.deleteWithKey(key)) }.map { true }

    fun deleteWithPredicate(predicate: Predicate<Entity>): Observable<Boolean> = defer { just(repository.deleteWithPredicate(predicate)) }.map { true }
}