package com.develop.zuzik.repository.sample.datasource.repository.realm.user

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.develop.zuzik.repository.asserts.RepositoryAssert
import com.develop.zuzik.repository.realm.RealmPredicate
import com.develop.zuzik.repository.sample.datasource.repository.UserRepositoryAssertStrategy
import com.develop.zuzik.repository.sample.datasource.repository.realm.RealmModule
import com.develop.zuzik.repository.sample.domain.entity.User
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmQuery
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


/**
 * User: zuzik
 * Date: 1/13/17
 */

@RunWith(AndroidJUnit4::class)
class UserRealmRepositoryTest {

    var realm: Realm? = null

    @Before
    fun setUp() {
        Realm.init(InstrumentationRegistry.getTargetContext())
        realm = Realm.getInstance(RealmConfiguration.Builder()
                .modules(RealmModule())
                .inMemory()
                .build())
    }

    @After
    fun tearDown() {
        realm?.executeTransaction(Realm::deleteAll)
    }

    @Test
    fun testRepositoryBehaviour() {
        RepositoryAssert(UserRepositoryAssertStrategy(
                { UserRealmRepository(realm!!) },
                { realm?.executeTransaction(Realm::deleteAll) },
                {
                    object : RealmPredicate<User, UserRealmObject> {
                        override fun where(query: RealmQuery<UserRealmObject>): RealmQuery<UserRealmObject> =
                                query.equalTo("id", it)
                    }
                })).assertRepositoryBehaviour()
    }

}