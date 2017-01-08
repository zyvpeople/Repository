package com.develop.zuzik.repository.sample.presentation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.develop.zuzik.repository.R
import com.develop.zuzik.repository.sample.application.app
import com.develop.zuzik.repository.sample.domain.entity_factory.RandomUserFactory
import com.develop.zuzik.repository.sample.presentation.filter.FilterFragment
import com.develop.zuzik.repository.sample.presentation.users.UsersFragment
import com.jakewharton.rxbinding.support.v7.widget.RxToolbar
import kotlinx.android.synthetic.main.activity_main.*
import rx.Observable
import rx.internal.util.SubscriptionList

class MainActivity : AppCompatActivity() {

    private val subscriptionList = SubscriptionList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val usersModel = app.usersModel

        addFragment(R.id.filterPlaceholder) { FilterFragment() }
        addFragment(R.id.usersPlaceholder) { UsersFragment() }

        toolbar.inflateMenu(R.menu.menu_users)

        val menuItem = RxToolbar.itemClicks(toolbar).share()
        val addUser = menuItem
                .filter { it.itemId == R.id.menuItemAddUser }
                .flatMap { Observable.just(RandomUserFactory().create()) }

        subscriptionList.add(usersModel.updateWithAddUserIntent(addUser))
        subscriptionList.add(usersModel.errorObservable.subscribe { Log.i("MainActivity", it.toString()) })
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
