package com.develop.zuzik.repository.sample.presentation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.develop.zuzik.repository.R
import com.develop.zuzik.repository.sample.application.app
import com.develop.zuzik.repository.sample.domain.entity_factory.RandomUserFactory
import com.develop.zuzik.repository.sample.presentation.filter.FilterFragment
import com.develop.zuzik.repository.sample.presentation.users.UsersFragment
import com.jakewharton.rxbinding.support.v7.widget.RxToolbar
import kotlinx.android.synthetic.main.activity_main.*
import rx.Observable.just
import rx.internal.util.SubscriptionList

class MainActivity : AppCompatActivity() {

    private val subscriptionList = SubscriptionList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val filterModel = app.filterModel
        val usersModel = app.usersModel
        val userFilterPredicateParameterFactory = app.userFilterPredicateParameterFactory

        addFragment(R.id.filterPlaceholder) { FilterFragment() }
        addFragment(R.id.usersPlaceholder) { UsersFragment() }

        toolbar.inflateMenu(R.menu.menu_users)

        val menuItem = RxToolbar.itemClicks(toolbar).share()
        val addUser = menuItem
                .filter { it.itemId == R.id.menuItemAddUser }
                .flatMap { just(RandomUserFactory().create()) }
        val removeUser = menuItem
                .filter { it.itemId == R.id.menuItemRemoveUser }
                .withLatestFrom(filterModel.stateObservable) { remove, filter -> filter }
                .map { userFilterPredicateParameterFactory.create(it) }

        subscriptionList.add(usersModel.addUser(addUser))
        subscriptionList.add(usersModel.removeUsers(removeUser))
        subscriptionList.add(usersModel.errorObservable.subscribe { Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show() })
    }

    private fun addFragment(placeholder: Int, createFragment: () -> Fragment) {
        val fragment = supportFragmentManager.findFragmentById(placeholder)
        if (fragment == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(placeholder, createFragment())
                    .commit()
        }
    }

    override fun onDestroy() {
        subscriptionList.unsubscribe()
        super.onDestroy()
    }
}
