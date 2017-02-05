package com.develop.zuzik.repository.core.exception

/**
 * User: zuzik
 * Date: 1/7/17
 */
class CreateEntityException(message: String?) : Exception(message ?: CreateEntityException::class.java.simpleName) {
    constructor() : this(null)

    object Factory {
        fun entityDoesNotHaveKey(entity: Any?) = CreateEntityException("Entity $entity does not have key")
    }
}