package com.develop.zuzik.repository.sample.domain.filter

import rx.Observable
import rx.subjects.BehaviorSubject

/**
 * User: zuzik
 * Date: 1/8/17
 */
class FilterModel(filter: Filter) {

    private val stateSubject = BehaviorSubject.create(filter)
    val stateObservable: Observable<Filter>
        get() = stateSubject.asObservable()

    fun update(updateStateIntent: Observable<Filter>) =
            updateStateIntent
                    .subscribe(stateSubject)
}