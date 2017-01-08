package com.develop.zuzik.repository.sample.domain.users

import android.util.Log
import com.develop.zuzik.repository.sample.datasource.repository.user.UserRepository
import com.develop.zuzik.repository.sample.domain.entity.User
import com.develop.zuzik.repository.sample.domain.filter.Filter
import rx.Observable
import rx.Observable.*
import rx.subjects.BehaviorSubject
import rx.subjects.PublishSubject

/**
 * User: zuzik
 * Date: 1/8/17
 */
class UsersModel(state: UsersModelState,
                 private val repository: UserRepository) {

    private val stateSubject = BehaviorSubject.create(state)
    private val errorSubject = PublishSubject.create<Throwable>()

    val stateObservable: Observable<UsersModelState>
        get() = stateSubject.asObservable()
    val errorObservable: Observable<Throwable>
        get() = errorSubject.asObservable()

    fun updateWithFilterIntent(filterIntent: Observable<Filter>) =
            filterIntent
                    .compose(skipErrorAndNotify())
                    .subscribe { Log.i("UsersModel", it.toString()) }

    fun updateWithAddUserIntent(addUserIntent: Observable<User>) =
            addUserIntent
                    .flatMap { defer { just(repository.create(it)) } }
                    .flatMap { defer { just(repository.readAll()) } }
                    .flatMap { users -> stateSubject.take(1).map { it.copy(users = users) } }
                    .compose(skipErrorAndNotify())
                    .subscribe(stateSubject)

    private fun <T> skipErrorAndNotify(): (Observable<T>) -> Observable<T> = {
        it
                .doOnError { errorSubject.onNext(it) }
                .onErrorResumeNext { empty() }
    }
}