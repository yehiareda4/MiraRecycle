package com.yehia.mira_recycle.view.base

import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    lateinit var baseFragment :BaseFragment

    fun superBackPressed() {
        super.onBackPressed()
    }

    override fun onBackPressed() {
        baseFragment.onBack();
    }

}