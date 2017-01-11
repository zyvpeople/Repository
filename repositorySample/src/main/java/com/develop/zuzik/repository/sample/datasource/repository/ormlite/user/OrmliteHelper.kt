package com.develop.zuzik.repository.sample.datasource.repository.ormlite.user

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils

/**
 * User: zuzik
 * Date: 1/11/17
 */
class OrmliteHelper(context: Context) : OrmLiteSqliteOpenHelper(context, "ormlitedatabase", null, 1) {

    override fun onCreate(database: SQLiteDatabase?, connectionSource: ConnectionSource?) {
        TableUtils.clearTable(connectionSource, UserOrmliteEntity::class.java)
    }

    override fun onUpgrade(database: SQLiteDatabase?, connectionSource: ConnectionSource?, oldVersion: Int, newVersion: Int) {
        TableUtils.dropTable<UserOrmliteEntity, Long>(connectionSource, UserOrmliteEntity::class.java, false)
        onCreate(database, connectionSource)
    }
}