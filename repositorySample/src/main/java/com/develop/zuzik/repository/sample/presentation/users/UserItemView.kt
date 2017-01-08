package com.develop.zuzik.repository.sample.presentation.users

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.develop.zuzik.repository.R
import com.develop.zuzik.repository.sample.domain.entity.User
import kotlinx.android.synthetic.main.view_user_item.view.*

/**
 * User: zuzik
 * Date: 1/8/17
 */
class UserItemView(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : LinearLayout(context, attrs, defStyleAttr) {

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?) : this(context, null)

    init {
        inflate(context, R.layout.view_user_item, this)
    }

    var user: User? = null
        set(value) {
            text.text = value.toString()
            field = value
        }
}