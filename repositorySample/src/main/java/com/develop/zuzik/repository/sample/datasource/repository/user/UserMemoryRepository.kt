package com.develop.zuzik.repository.sample.datasource.repository.user

import com.develop.zuzik.repository.core.key_generator.LongKeyGenerator
import com.develop.zuzik.repository.memory.MemoryRepository
import com.develop.zuzik.repository.sample.domain.entity.User

/**
 * User: zuzik
 * Date: 1/8/17
 */
class UserMemoryRepository : MemoryRepository<User, Long>(
        LongKeyGenerator(),
        User::id,
        { user, id -> user.copy(id = id) },
        { it.copy() }), UserRepository