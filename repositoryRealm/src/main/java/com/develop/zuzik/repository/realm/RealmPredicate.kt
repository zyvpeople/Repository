package com.develop.zuzik.repository.realm

import com.develop.zuzik.repository.core.Predicate
import io.realm.RealmModel
import io.realm.RealmQuery

/**
 * User: zuzik
 * Date: 1/11/17
 */
interface RealmPredicate<in Entity, RealmEntity : RealmModel> : Predicate<Entity> {
    fun where(query: RealmQuery<RealmEntity>): RealmQuery<RealmEntity>
}