package com.develop.zuzik.repository.core.exception

/**
 * User: zuzik
 * Date: 1/7/17
 */
class UpdateEntityException(message: String?) : Exception(message ?: UpdateEntityException::class.java.simpleName) {
    constructor() : this(null)

    object Factory {
        fun entityDoesNotHaveKey(entity: Any?) = UpdateEntityException("Entity $entity does not have key")
        fun unreachableSituation() = UpdateEntityException("Unreachable situation")
    }
}