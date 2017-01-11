package com.develop.zuzik.repository.sample.datasource.repository.realm.user

import io.realm.RealmObject

/**
 * User: zuzik
 * Date: 1/11/17
 */
open class UserRealmObject : RealmObject() {

    var id: Long = 0
    var name: String = ""
    var age: Int = 0
}