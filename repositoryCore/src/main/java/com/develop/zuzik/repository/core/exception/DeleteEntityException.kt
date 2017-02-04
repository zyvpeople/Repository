package com.develop.zuzik.repository.core.exception

/**
 * User: zuzik
 * Date: 1/7/17
 */
class DeleteEntityException(message: String?) : Exception(message ?: DeleteEntityException::class.java.simpleName) {
    constructor() : this(null)

    object Factory {
        fun entityDoesNotHaveKey(entity: Any?) = DeleteEntityException("Entity $entity does not have key")
        fun entityWithKeyDoesNotExist(key: Any?) = DeleteEntityException("Entity with key $key does not exist")
    }
}