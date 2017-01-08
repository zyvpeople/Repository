package com.develop.zuzik.repository.core.key_generator

import com.develop.zuzik.repository.core.KeyGenerator
import com.develop.zuzik.repository.core.exception.GenerateKeyException

/**
 * User: zuzik
 * Date: 1/7/17
 */
class LongKeyGenerator : KeyGenerator<Long> {

    private var _key = 0L

    override val key: Long
        get() = _key

    override fun generate(): KeyGenerator<Long> {
        if (_key == Long.MAX_VALUE) {
            throw GenerateKeyException()
        } else {
            _key++
            return this
        }
    }
}