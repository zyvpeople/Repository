package com.develop.zuzik.repository.memory

import com.develop.zuzik.repository.core.Predicate

/**
 * User: zuzik
 * Date: 1/7/17
 */
interface MemoryPredicate<in Entity> : Predicate<Entity> {

    fun satisfied(entity: Entity): Boolean
}