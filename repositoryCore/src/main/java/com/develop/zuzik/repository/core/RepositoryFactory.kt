package com.develop.zuzik.repository.core

/**
 * User: zuzik
 * Date: 1/7/17
 */
interface RepositoryFactory<Entity, Id> {
    fun create(): Repository<Entity, Id>
}