package com.develop.zuzik.repository.sample.domain.users

import com.develop.zuzik.repository.core.Predicate
import com.develop.zuzik.repository.rx.RxRepository
import com.develop.zuzik.repository.sample.datasource.repository.user.UserRepository
import com.develop.zuzik.repository.sample.domain.entity.User
import rx.Observable
import rx.Observable.combineLatest
import rx.Observable.empty
import rx.Subscription
import rx.subjects.BehaviorSubject
import rx.subjects.PublishSubject

/**
 * User: zuzik
 * Date: 1/8/17
 */
class UsersModel(state: UsersModelState,
                 repository: UserRepository) {

    private val repository: RxRepository<User, Long> = RxRepository(repository)
    private val stateSubject = BehaviorSubject.create(state)
    private val errorSubject = PublishSubject.create<Throwable>()
    private val filterSubject = PublishSubject.create<Predicate<User>>()
    private val repositoryChangedSubject = BehaviorSubject.create(Any())

    init {
        combineLatest(filterSubject, repositoryChangedSubject) { filter, repositoryChangedSignal -> filter }
                .flatMap { this.repository.readWithPredicate(it) }
                .flatMap { users -> stateSubject.take(1).map { it.copy(users = users) } }
                .compose(skipErrorAndNotify())
                .subscribe(stateSubject)
    }

    val stateObservable: Observable<UsersModelState>
        get() = stateSubject.asObservable()
    val errorObservable: Observable<Throwable>
        get() = errorSubject.asObservable()

    fun filter(filterIntent: Observable<Predicate<User>>): Subscription =
            filterIntent
                    .compose(skipErrorAndNotify())
                    .subscribe(filterSubject)

    fun addUser(addUserIntent: Observable<User>): Subscription =
            addUserIntent
                    .flatMap { repository.create(it) }
                    .map { Any() }
                    .compose(skipErrorAndNotify())
                    .subscribe(repositoryChangedSubject)

    fun removeUsers(removeUsersIntent: Observable<Predicate<User>>): Subscription =
            removeUsersIntent
                    .flatMap { repository.deleteWithPredicate(it) }
                    .map { Any() }
                    .compose(skipErrorAndNotify())
                    .subscribe(repositoryChangedSubject)

    private fun <T> skipErrorAndNotify(): (Observable<T>) -> Observable<T> = {
        it
                .doOnError { errorSubject.onNext(it) }
                .onErrorResumeNext { empty() }
    }
}