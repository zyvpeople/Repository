package com.develop.zuzik.repository.core.exception

/**
 * User: zuzik
 * Date: 1/7/17
 */
class ReadEntityException(message: String?) : Exception(message ?: ReadEntityException::class.java.simpleName) {
    constructor() : this(null)

    object Factory {
        fun entityWithKeyDoesNotExist(key: Any?) = ReadEntityException("Entity with key $key does not exist")
    }
}