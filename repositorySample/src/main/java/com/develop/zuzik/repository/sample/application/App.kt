package com.develop.zuzik.repository.sample.application

import android.app.Application
import android.content.Context
import com.activeandroid.ActiveAndroid
import com.activeandroid.Configuration
import com.develop.zuzik.repository.core.PredicateParameterFactory
import com.develop.zuzik.repository.sample.datasource.repository.UserRepository
import com.develop.zuzik.repository.sample.datasource.repository.activeandroid.user.UserActiveAndroidModel
import com.develop.zuzik.repository.sample.datasource.repository.activeandroid.user.UserActiveAndroidRepository
import com.develop.zuzik.repository.sample.datasource.repository.activeandroid.user.UserFilterActiveAndroidPredicateParameterFactory
import com.develop.zuzik.repository.sample.datasource.repository.memory.user.UserFilterMemoryPredicateParameterFactory
import com.develop.zuzik.repository.sample.datasource.repository.memory.user.UserMemoryRepository
import com.develop.zuzik.repository.sample.datasource.repository.ormlite.OrmliteHelper
import com.develop.zuzik.repository.sample.datasource.repository.ormlite.user.UserFilterOrmlitePredicateParameterFactory
import com.develop.zuzik.repository.sample.datasource.repository.ormlite.user.UserOrmliteEntity
import com.develop.zuzik.repository.sample.datasource.repository.ormlite.user.UserOrmliteRepository
import com.develop.zuzik.repository.sample.datasource.repository.realm.RealmModule
import com.develop.zuzik.repository.sample.datasource.repository.realm.user.UserFilterRealmPredicateParameterFactory
import com.develop.zuzik.repository.sample.datasource.repository.realm.user.UserRealmRepository
import com.develop.zuzik.repository.sample.domain.entity.User
import com.develop.zuzik.repository.sample.domain.filter.Filter
import com.develop.zuzik.repository.sample.domain.filter.FilterModel
import com.develop.zuzik.repository.sample.domain.users.UsersModel
import com.develop.zuzik.repository.sample.domain.users.UsersModelState
import com.j256.ormlite.android.apptools.OpenHelperManager
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * User: zuzik
 * Date: 1/8/17
 */
//FIXME: when device does not have memory - app crashes when tries to init realm - database cant be created
class App : Application() {

    private enum class RepositoryType {
        MEMORY,
        REALM,
        ORMLITE,
        ACTIVE_ANDROID
    }

    private val repositoryType = RepositoryType.ACTIVE_ANDROID

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
        repositoryConfiguration = repositoryConfiguration(this)

        userRepository = repositoryConfiguration.createUserRepository()
        userFilterPredicateParameterFactory = repositoryConfiguration.createUserFilterPredicateFactory()
        usersModel = UsersModel(UsersModelState(emptyList()), userRepository)

        usersModel
                .filter(filterModel
                        .stateObservable
                        .map { userFilterPredicateParameterFactory.create(it) })
    }

    private fun repositoryConfiguration(context: Context): RepositoryConfiguration =
            when (repositoryType) {
                App.RepositoryType.MEMORY -> MemoryRepositoryConfiguration()
                App.RepositoryType.REALM -> RealmRepositoryConfiguration(context)
                App.RepositoryType.ORMLITE -> OrmliteRepositoryConfiguration(context)
                App.RepositoryType.ACTIVE_ANDROID -> ActiveAndroidRepositoryConfiguration(context)
            }

    private interface RepositoryConfiguration {
        fun createUserRepository(): UserRepository
        fun createUserFilterPredicateFactory(): PredicateParameterFactory<User, Filter>
    }

    private class MemoryRepositoryConfiguration : RepositoryConfiguration {

        override fun createUserRepository() = UserMemoryRepository()

        override fun createUserFilterPredicateFactory() = UserFilterMemoryPredicateParameterFactory()
    }

    private class RealmRepositoryConfiguration(context: Context) : RepositoryConfiguration {

        private val realm: Realm = Realm.getInstance(RealmConfiguration.Builder().modules(RealmModule()).build())

        init {
            Realm.init(context)
        }

        override fun createUserRepository() = UserRealmRepository(realm)

        override fun createUserFilterPredicateFactory() = UserFilterRealmPredicateParameterFactory()
    }

    private class OrmliteRepositoryConfiguration(context: Context) : RepositoryConfiguration {

        private val ormliteHelper: OrmLiteSqliteOpenHelper = OpenHelperManager.getHelper(context, OrmliteHelper::class.java)

        override fun createUserRepository() = UserOrmliteRepository(ormliteHelper.getDao(UserOrmliteEntity::class.java))

        override fun createUserFilterPredicateFactory() = UserFilterOrmlitePredicateParameterFactory()
    }

    private class ActiveAndroidRepositoryConfiguration(context: Context) : RepositoryConfiguration {

        init {
            val databaseConfiguration = Configuration.Builder(context)
                    .setDatabaseName("activeandroid.db")
                    .setDatabaseVersion(1)
                    .addModelClass(UserActiveAndroidModel::class.java)
                    .create()
            ActiveAndroid.initialize(databaseConfiguration)
        }

        override fun createUserRepository() = UserActiveAndroidRepository()

        override fun createUserFilterPredicateFactory() = UserFilterActiveAndroidPredicateParameterFactory()
    }
}