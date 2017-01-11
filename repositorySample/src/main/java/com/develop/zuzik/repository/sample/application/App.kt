package com.develop.zuzik.repository.sample.application

import android.app.Application
import android.content.Context
import com.develop.zuzik.repository.core.PredicateParameterFactory
import com.develop.zuzik.repository.sample.datasource.repository.UserRepository
import com.develop.zuzik.repository.sample.datasource.repository.memory.user.UserFilterMemoryPredicateParameterFactory
import com.develop.zuzik.repository.sample.datasource.repository.memory.user.UserMemoryRepository
import com.develop.zuzik.repository.sample.datasource.repository.realm.RealmModule
import com.develop.zuzik.repository.sample.datasource.repository.realm.user.UserFilterRealmPredicateParameterFactory
import com.develop.zuzik.repository.sample.datasource.repository.realm.user.UserRealmRepository
import com.develop.zuzik.repository.sample.domain.entity.User
import com.develop.zuzik.repository.sample.domain.filter.Filter
import com.develop.zuzik.repository.sample.domain.filter.FilterModel
import com.develop.zuzik.repository.sample.domain.users.UsersModel
import com.develop.zuzik.repository.sample.domain.users.UsersModelState
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * User: zuzik
 * Date: 1/8/17
 */
//FIXME: when device does not have memory - app crashes when tries to init realm - database cant be created
class App : Application() {

    private val filter = Filter(null, null)
    val filterModel: FilterModel = FilterModel(filter)
    lateinit var userRepository: UserRepository
        private set
    lateinit var userFilterPredicateParameterFactory: PredicateParameterFactory<User, Filter>
        private set
    lateinit var usersModel: UsersModel
        private set
    lateinit private var repositoryConfiguration: RepositoryConfiguration

    override fun onCreate() {
        super.onCreate()
        repositoryConfiguration = repositoryConfiguration()
        repositoryConfiguration.init(this)

        userRepository = repositoryConfiguration.createUserRepository()
        userFilterPredicateParameterFactory = repositoryConfiguration.createUserFilterPredicateFactory()
        usersModel = UsersModel(UsersModelState(emptyList()), userRepository)

        usersModel
                .filter(filterModel
                        .stateObservable
                        .map { userFilterPredicateParameterFactory.create(it) })
    }

    private fun repositoryConfiguration(): RepositoryConfiguration {
        val realm = true
        return if (realm) {
            RealmRepositoryConfiguration()
        } else {
            MemoryRepositoryConfiguration()
        }
    }

    private interface RepositoryConfiguration {
        fun init(context: Context)
        fun createUserRepository(): UserRepository
        fun createUserFilterPredicateFactory(): PredicateParameterFactory<User, Filter>
    }

    private class MemoryRepositoryConfiguration : RepositoryConfiguration {
        override fun init(context: Context) {
        }

        override fun createUserRepository() = UserMemoryRepository()

        override fun createUserFilterPredicateFactory() = UserFilterMemoryPredicateParameterFactory()
    }

    private class RealmRepositoryConfiguration : RepositoryConfiguration {
        override fun init(context: Context) {
            Realm.init(context)
        }

        override fun createUserRepository() = UserRealmRepository(Realm.getInstance(RealmConfiguration.Builder().modules(RealmModule()).build()))

        override fun createUserFilterPredicateFactory() = UserFilterRealmPredicateParameterFactory()
    }
}