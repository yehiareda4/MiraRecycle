package com.reda.yehia.mairrecycle

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.reda.yehia.mairrecycle.databinding.MiraRecycleViewV3LayoutEditionBinding

class MiraRecycleViewV3Edition : RelativeLayout {

    var context1: Context
    lateinit var inflter: LayoutInflater
    lateinit var onEndLess: OnEndLessK
    lateinit var binding: MiraRecycleViewV3LayoutEditionBinding

    var maxPage = 0
    var loadMore: LoadMoreK? = null

    constructor(context: Context) : super(context) {
        this.context1 = context
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        this.context1 = context
        initView(
            context,
            attrs,
            0
        )
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        this.context1 = context
        initView(
            context,
            attrs,
            defStyleAttr
        )
    }

    private fun initView() {
        inflter = LayoutInflater.from(context1)
        binding = DataBindingUtil.inflate(
            inflter, R.layout.mira_recycle_view_v3_layout_edition, this, false
        )
        addView(binding.root)
    }

    @SuppressLint("Recycle", "CustomViewStyleable")
    private fun initView(context: Context, attrs: AttributeSet, defStyleAttr: Int) {
        inflter = LayoutInflater.from(context1)
        binding = DataBindingUtil.inflate(
            inflter, R.layout.mira_recycle_view_v3_layout, this, false
        )

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.MiraRecycleViewV3, defStyleAttr, 0
        )

        val shimmerLayout =
            typedArray.getResourceId(R.styleable.MiraRecycleViewV3_mira_shimmer_layout, 0)
        var visibility = typedArray.getInt(R.styleable.MiraRecycleViewV3_mira_visibility, View.GONE)
        when (visibility) {
            0 -> {
                visibility = View.VISIBLE
            }
            1 -> {
                visibility = View.INVISIBLE
            }
            2 -> {
                visibility = View.GONE
            }
        }
        val attrsEnabled =
            typedArray.getBoolean(R.styleable.MiraRecycleViewV3_mira_attrs_enabled, false)

        if (attrsEnabled) {
            if (shimmerLayout != 0) {
                setMiraRecycleViewSFlShimmer(
                    shimmerLayout,
                )
                enabledMiraShimmerLoading(visibility)
            }
        }

        addView(binding.root)
    }

    fun setUp(
        shimmerLayout: Int,
        manger: RecyclerView.LayoutManager,
        loadMore: LoadMoreK?
    ) {
        if (shimmerLayout != 0) {
            val view = createView(shimmerLayout)
        }

        this.loadMore = loadMore
        setUpMiraRecycleView(manger)
        stopLoad(0)
        this.loadMore?.onInit()
    }

    fun setUp(
        manger: RecyclerView.LayoutManager,
        loadMore: LoadMoreK?
    ) {
        this.loadMore = loadMore
        setUpMiraRecycleView(manger)
        stopLoad(0)
        this.loadMore?.onInit()
    }

    fun enabledMiraShimmerLoading(visibility: Int) {
        binding.miraRecycleViewLyShimmer.miraRecycleViewSFlLoading.visibility = visibility
        if (visibility == VISIBLE) {
            binding.miraRecycleViewLyShimmer.miraRecycleViewSFlLoading.startShimmer()
        } else {
            binding.miraRecycleViewLyShimmer.miraRecycleViewSFlLoading.stopShimmer()
        }
    }

    private fun setMiraRecycleViewSFlShimmer(
        shimmerLayout: Int
    ) {
        if (shimmerLayout != 0) {
            binding.miraRecycleViewLyShimmer.miraRecycleViewTvShimmer.setImageResource(shimmerLayout)
        }
    }

    private fun createView(shimmerLayout: Int): View {
        val view = inflate(context1, shimmerLayout, null)
        view.layoutParams = LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT,
            1f
        )
        return view
    }

    fun stopLoad(page: Int) {
        resetPresses()
    }

    private fun resetPresses() {
        enabledMiraShimmerLoading(GONE)

        maxPage = 0
        reset()
    }

    private fun reset() {
        onEndLess.previousTotal = 0
        onEndLess.current_page = 1
        onEndLess.previous_page = 1
        maxPage = 0
    }

    fun setAdapter(adapter: RecyclerView.Adapter<*>?) {
        binding.miraRecycleViewRvRecycler.adapter = adapter
    }

    private fun setUpMiraRecycleView(manger: RecyclerView.LayoutManager) {
        binding.miraRecycleViewRvRecycler.layoutManager = manger
    }

}