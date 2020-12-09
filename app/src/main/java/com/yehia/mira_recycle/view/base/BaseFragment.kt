package com.yehia.mira_recycle.view.base

import androidx.fragment.app.Fragment

import android.view.View
import androidx.appcompat.app.AlertDialog
import com.yehia.mira_recycle.R
import com.yehia.mira_recycle.util.HelperMethod

open class BaseFragment : Fragment() {

    lateinit var baseActivity: BaseActivity
    protected lateinit var builder: AlertDialog.Builder

    open fun setUp() {
        baseActivity = activity as BaseActivity;
        baseActivity.baseFragment = this
    }

    open fun setOnClick(view: View) {
        view.setOnClickListener { onViewClicked(view) }
    }

    open fun onViewClicked(view: View) {
        HelperMethod.hiddenKeyBord(view, requireActivity())
    }

    open fun onBack() {
        baseActivity.superBackPressed()
    }

    fun Boolean.showAlertDialog(message: String) {
        builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.please_wait))
        builder.setMessage(message)
        builder.setCancelable(this)
        builder.show()
    }
}