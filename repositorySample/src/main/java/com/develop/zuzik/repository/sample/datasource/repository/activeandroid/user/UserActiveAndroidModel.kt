package com.develop.zuzik.repository.sample.datasource.repository.activeandroid.user

import com.activeandroid.Model
import com.activeandroid.annotation.Column
import com.activeandroid.annotation.Table

/**
 * User: zuzik
 * Date: 1/12/17
 */
@Table(name = "users")
class UserActiveAndroidModel : Model() {
    @Column(name = "name")
    var name: String = ""
    @Column(name = "age")
    var age: Int = 0
}