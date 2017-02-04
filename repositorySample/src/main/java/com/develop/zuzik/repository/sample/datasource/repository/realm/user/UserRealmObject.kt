package com.develop.zuzik.repository.sample.datasource.repository.realm.user

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * User: zuzik
 * Date: 1/11/17
 */
open class UserRealmObject : RealmObject() {

    @PrimaryKey
    var id: Long = 0
    var name: String = ""
    var age: Int = 0
}