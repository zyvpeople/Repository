package com.develop.zuzik.repository.core.exception

/**
 * User: zuzik
 * Date: 1/7/17
 */
class GenerateKeyException(message: String?) : Exception(message ?: GenerateKeyException::class.java.simpleName) {
    constructor() : this(null)
}