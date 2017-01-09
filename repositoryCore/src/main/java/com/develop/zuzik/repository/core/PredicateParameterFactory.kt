package com.develop.zuzik.repository.core

/**
 * User: zuzik
 * Date: 1/9/17
 */
interface PredicateParameterFactory<in Entity, in Parameter> {
    fun create(parameter: Parameter): Predicate<Entity>
}