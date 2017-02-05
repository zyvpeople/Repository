package com.develop.zuzik.repository.sample.datasource.repository.ormlite.user

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.develop.zuzik.repository.asserts.RepositoryAssert
import com.develop.zuzik.repository.ormlite.OrmlitePredicate
import com.develop.zuzik.repository.sample.datasource.repository.UserRepositoryAssertStrategy
import com.develop.zuzik.repository.sample.datasource.repository.ormlite.OrmliteHelper
import com.develop.zuzik.repository.sample.domain.entity.User
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.stmt.Where
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


/**
 * User: zuzik
 * Date: 1/13/17
 */

@RunWith(AndroidJUnit4::class)
class UserOrmliteRepositoryTest {

    var dao: Dao<UserOrmliteEntity, Long>? = null

    @Before
    fun setUp() {
        val helper = OpenHelperManager.getHelper(InstrumentationRegistry.getTargetContext(), OrmliteHelper::class.java)
        OpenHelperManager.releaseHelper()
        dao = helper.getDao(UserOrmliteEntity::class.java)
    }

    @After
    fun tearDown() {
        dao?.connectionSource?.close()
        dao = null
        InstrumentationRegistry.getTargetContext().deleteDatabase("ormlitedatabase")
    }

    @Test
    fun testRepositoryBehaviour() {
        RepositoryAssert(UserRepositoryAssertStrategy(
                {
                    if (dao == null) {
                        val helper = OpenHelperManager.getHelper(InstrumentationRegistry.getTargetContext(), OrmliteHelper::class.java)
                        OpenHelperManager.releaseHelper()
                        dao = helper.getDao(UserOrmliteEntity::class.java)
                    }
                    UserOrmliteRepository(dao!!)
                },
                {
                    dao?.connectionSource?.close()
                    dao = null
                    InstrumentationRegistry.getTargetContext().deleteDatabase("ormlitedatabase")
                },
                {
                    object : OrmlitePredicate<User, Long, UserOrmliteEntity> {
                        override fun where(where: Where<UserOrmliteEntity, Long>): Where<UserOrmliteEntity, Long>? =
                                where.eq("id", it)
                    }
                })).assertRepositoryBehaviour()
    }
}