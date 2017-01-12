package com.develop.zuzik.repository.activeandroid

import com.activeandroid.query.From
import com.develop.zuzik.repository.core.Predicate

/**
 * User: zuzik
 * Date: 1/12/17
 */
interface ActiveAndroidPredicate<in Entity> : Predicate<Entity> {
    fun query(from: From): From
}