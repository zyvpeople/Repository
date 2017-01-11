package com.develop.zuzik.repository.realm.key_generator

import com.develop.zuzik.repository.core.exception.GenerateKeyException
import com.develop.zuzik.repository.realm.KeyGenerator
import io.realm.RealmModel
import io.realm.RealmQuery

/**
 * User: zuzik
 * Date: 1/11/17
 */
class LongKeyGenerator<RealmEntity : RealmModel>(private val keyPropertyName: String) : KeyGenerator<Long, RealmEntity> {

    @Throws(GenerateKeyException::class)
    override fun generate(query: RealmQuery<RealmEntity>): Long {
        try {
            val maxKey = query.max(keyPropertyName)?.toLong()
            return if (maxKey == null) {
                1L
            } else if (maxKey != Long.MAX_VALUE) {
                return maxKey + 1
            } else {
                throw GenerateKeyException()
            }
        } catch (e: IllegalArgumentException) {
            throw GenerateKeyException()
        }
    }
}