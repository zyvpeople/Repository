package com.develop.zuzik.repository.sample.datasource.repository.memory.user

import android.support.test.runner.AndroidJUnit4
import com.develop.zuzik.repository.asserts.RepositoryAssert
import com.develop.zuzik.repository.memory.MemoryPredicate
import com.develop.zuzik.repository.sample.datasource.repository.UserRepositoryAssertStrategy
import com.develop.zuzik.repository.sample.domain.entity.User
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * User: zuzik
 * Date: 1/29/17
 */
@RunWith(AndroidJUnit4::class)
class UserMemoryRepositoryTest {
    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {

    }

    @Test
    fun testRepositoryBehaviour() {
        RepositoryAssert(UserRepositoryAssertStrategy(
                { UserMemoryRepository() },
                {},
                {
                    object : MemoryPredicate<User> {
                        override fun satisfied(entity: User) = entity.id == it
                    }
                })).assertRepositoryBehaviour()
    }

}