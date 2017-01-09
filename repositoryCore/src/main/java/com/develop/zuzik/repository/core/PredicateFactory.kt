package com.develop.zuzik.repository.core

/**
 * User: zuzik
 * Date: 1/9/17
 */
interface PredicateFactory<in Entity> {
    fun create(): Predicate<Entity>
}