package com.develop.zuzik.repository.memory

import com.develop.zuzik.repository.core.exception.GenerateKeyException

/**
 * User: zuzik
 * Date: 1/7/17
 */
interface KeyGenerator<Key> {
    val key: Key
    @Throws(GenerateKeyException::class)
    fun generate(): KeyGenerator<Key>
}