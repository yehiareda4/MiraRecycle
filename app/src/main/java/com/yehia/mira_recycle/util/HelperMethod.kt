package com.yehia.mira_recycle.util

import android.content.Context
import android.transition.TransitionManager
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.RelativeLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.yehia.mira_recycle.R

class HelperMethod {
    companion object {

        fun hiddenKeyBord(view: View, context: Context) {
            val imm =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun editHeight(view: View, SubView: RelativeLayout?, open: Boolean, context: Context) {
            if (open) {
                TransitionManager.beginDelayedTransition(SubView)
                val appear = view.layoutParams
                appear.width = ViewGroup.LayoutParams.MATCH_PARENT
                appear.height = ViewGroup.LayoutParams.MATCH_PARENT
                view.layoutParams = appear
            } else {
                val appear = view.layoutParams
                appear.height = context.resources.getDimension(R.dimen._530sdp).toInt()
                appear.width = context.resources.getDimension(R.dimen._250sdp).toInt()
                view.layoutParams = appear
            }
        }

        fun editHeight(
            view: View,
            SubView: ConstraintLayout?,
            open: Boolean,
            height: Int,
            width: Int
        ) {
            if (open) {
                TransitionManager.beginDelayedTransition(SubView)
                val appear = view.layoutParams
                appear.width = ViewGroup.LayoutParams.MATCH_PARENT
                appear.height = ViewGroup.LayoutParams.MATCH_PARENT
                view.layoutParams = appear
            } else {
                val appear = view.layoutParams
                appear.height = height
                appear.width = width
                view.layoutParams = appear
            }
        }

    }
}