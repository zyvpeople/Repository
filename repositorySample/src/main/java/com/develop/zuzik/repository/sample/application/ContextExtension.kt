package com.develop.zuzik.repository.sample.application

import android.content.Context
import android.content.ContextWrapper

/**
 * User: zuzik
 * Date: 1/8/17
 */

val Context.application: App
    get() = ContextWrapper(this).applicationContext as App