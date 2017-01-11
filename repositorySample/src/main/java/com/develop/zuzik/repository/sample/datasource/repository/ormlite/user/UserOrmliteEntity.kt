package com.develop.zuzik.repository.sample.datasource.repository.ormlite.user

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

/**
 * User: zuzik
 * Date: 1/11/17
 */
@DatabaseTable(tableName = "users")
class UserOrmliteEntity {

    @DatabaseField(generatedId = true)
    var id: Long = 0
    @DatabaseField(columnName = "name")
    var name: String = ""
    @DatabaseField(columnName = "age")
    var age: Int = 0
}