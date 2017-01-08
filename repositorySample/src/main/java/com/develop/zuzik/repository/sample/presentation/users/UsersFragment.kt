package com.develop.zuzik.repository.sample.presentation.users

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.develop.zuzik.repository.R
import com.develop.zuzik.repository.sample.application.app
import kotlinx.android.synthetic.main.fragment_users.*
import rx.internal.util.SubscriptionList

/**
 * User: zuzik
 * Date: 1/8/17
 */
class UsersFragment : Fragment() {

    private val adapter = UsersAdapter()
    private val subscriptionList = SubscriptionList()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        subscriptionList.add(
                context.app.usersModel.stateObservable.subscribe {
                    adapter.users = it.users
                    adapter.notifyDataSetChanged()
                })
    }

    override fun onDestroyView() {
        subscriptionList.unsubscribe()
        super.onDestroyView()
    }
}