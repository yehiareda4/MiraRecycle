package com.reda.yehia.mairrecycle.util

/*
 * Yehia Reda
 * */
abstract class LoadMoreK {
    abstract fun onLoadMore(current_page: Int)
    abstract fun onRefresh()
    abstract fun onReset()
    abstract fun onInit()
}