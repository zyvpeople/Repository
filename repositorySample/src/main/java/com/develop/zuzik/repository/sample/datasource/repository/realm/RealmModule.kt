package com.develop.zuzik.repository.sample.datasource.repository.realm

import com.develop.zuzik.repository.sample.datasource.repository.realm.user.UserRealmObject
import io.realm.annotations.RealmModule

/**
 * User: zuzik
 * Date: 1/11/17
 */
@RealmModule(classes = arrayOf(UserRealmObject::class))
class RealmModule