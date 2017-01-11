package com.develop.zuzik.repository.realm

import com.develop.zuzik.repository.core.exception.GenerateKeyException
import io.realm.RealmModel
import io.realm.RealmQuery

/**
 * User: zuzik
 * Date: 1/11/17
 */
interface KeyGenerator<out Key, RealmEntity : RealmModel> {

    @Throws(GenerateKeyException::class)
    fun generate(query: RealmQuery<RealmEntity>): Key
}