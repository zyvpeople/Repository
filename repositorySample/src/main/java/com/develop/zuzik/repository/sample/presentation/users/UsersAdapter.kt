package com.develop.zuzik.repository.sample.presentation.users

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.develop.zuzik.repository.sample.domain.entity.User

/**
 * User: zuzik
 * Date: 1/8/17
 */
class UsersAdapter : RecyclerView.Adapter<UsersAdapter.UserItemViewHolder>() {

    class UserItemViewHolder(val view: UserItemView) : RecyclerView.ViewHolder(view) {
        init {
            view.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
        }
    }

    var users = emptyList<User>()

    override fun getItemCount(): Int = users.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): UserItemViewHolder =
            UserItemViewHolder(UserItemView(parent?.context))

    override fun onBindViewHolder(holder: UserItemViewHolder?, position: Int) {
        holder?.view?.user = users[position]
    }
}