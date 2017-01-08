package com.develop.zuzik.repository.sample.domain.users

import android.util.Log
import com.develop.zuzik.repository.sample.domain.filter.Filter
import rx.Observable
import rx.subjects.BehaviorSubject

/**
 * User: zuzik
 * Date: 1/8/17
 */
class UsersModel(state: UsersModelState) {

    private val stateSubject = BehaviorSubject.create(state)
    val stateObservable: Observable<UsersModelState>
        get() = stateSubject.asObservable()

    fun updateWithFilter(filterIntent: Observable<Filter>) =
            filterIntent.subscribe { Log.i("UsersModel", it.toString()) }
}