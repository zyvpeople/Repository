package com.develop.zuzik.repository.ormlite

import com.develop.zuzik.repository.core.Predicate
import com.j256.ormlite.stmt.Where

/**
 * User: zuzik
 * Date: 1/11/17
 */
interface OrmlitePredicate<in Entity, Key, OrmliteEntity> : Predicate<Entity> {
    fun where(where: Where<OrmliteEntity, Key>): Where<OrmliteEntity, Key>?
}