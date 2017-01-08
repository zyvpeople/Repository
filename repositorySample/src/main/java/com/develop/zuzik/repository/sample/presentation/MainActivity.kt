package com.develop.zuzik.repository.sample.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.develop.zuzik.repository.R
import com.develop.zuzik.repository.sample.presentation.filter.FilterFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val filterFragment = supportFragmentManager.findFragmentById(R.id.filterPlaceholder)
        if (filterFragment == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.filterPlaceholder, FilterFragment())
                    .commit()
        }
    }
}
