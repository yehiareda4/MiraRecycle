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
import com.reda.yehia.mairrecycle.databinding.MiraRecycleViewV3LayoutBinding

class MiraRecycleViewV3 : RelativeLayout {

    val GIF = "GIF"
    val OTHER = "OTHER"

    var context1: Context
    lateinit var inflter: LayoutInflater
    lateinit var onEndLess: OnEndLessK
    lateinit var binding: MiraRecycleViewV3LayoutBinding

    var maxPage = 0
    var loadMore: LoadMoreK? = null
    private var refreshing = true
    var top = false

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
            inflter, R.layout.mira_recycle_view_v3_layout, this, false
        )
        addView(binding.root)
    }

    @SuppressLint("Recycle")
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
        val countRowsShimmer =
            typedArray.getInt(R.styleable.MiraRecycleViewV3_mira_count_rows_shimmer, 0)
        val countColumnsShimmer =
            typedArray.getInt(R.styleable.MiraRecycleViewV3_mira_count_columns_shimmer, 0)
        var visibility = typedArray.getInt(R.styleable.MiraRecycleViewV3_mira_visibility, View.GONE)
        if (visibility == 0) {
            visibility = View.VISIBLE
        } else if (visibility == 1) {
            visibility = View.INVISIBLE
        } else if (visibility == 2) {
            visibility = View.GONE
        }
        val refreshing = typedArray.getBoolean(R.styleable.MiraRecycleViewV3_mira_refreshing, true)
        val attrsEnabled =
            typedArray.getBoolean(R.styleable.MiraRecycleViewV3_mira_attrs_enabled, false)
        val errorImage = typedArray.getInt(R.styleable.MiraRecycleViewV3_mira_error_image, 0)
        var errorText = typedArray.getString(R.styleable.MiraRecycleViewV3_mira_error_message)
        var actionText = typedArray.getString(R.styleable.MiraRecycleViewV3_mira_error_title)

        if (attrsEnabled) {
            if (shimmerLayout != 0) {
                setMiraRecycleViewSFlShimmer(
                    shimmerLayout,
                    countRowsShimmer,
                    countColumnsShimmer
                )
                enabledMiraShimmerLoading(visibility)
            }
            this.refreshing = refreshing
            binding.miraRecycleViewSrlRefresh.isEnabled = refreshing

            if (errorText.isNullOrEmpty()) {
                errorText = ""
            }
            if (actionText.isNullOrEmpty()) {
                actionText = ""
            }
            enabledMiraError(
                GONE,
                errorImage,
                errorText,
                actionText,
                null
            )
        }

        addView(binding.root)
    }


    fun setUp(
        shimmerLayout: Int,
        countRowsShimmer: Int,
        countColumnsShimmer: Int,
        manger: RecyclerView.LayoutManager,
        refreshing: Boolean,
        loadMore: LoadMoreK?
    ) {
        setMiraRecycleViewSFlShimmer(
            shimmerLayout,
            countRowsShimmer,
            countColumnsShimmer
        )
        this.refreshing = refreshing
        binding.miraRecycleViewSrlRefresh.isEnabled = refreshing
        this.loadMore = loadMore
        setUpMiraRecycleView(manger)
        stopLoad(0)
        this.loadMore?.onInit()
    }

    fun setUp(
        shimmerLayout: Int,
        manger: RecyclerView.LayoutManager,
        refreshing: Boolean,
        loadMore: LoadMoreK?
    ) {
        if (shimmerLayout != 0) {
            binding.miraRecycleViewLyShimmer.miraRecycleViewLlShimmer.removeAllViews()
            val view = createView(shimmerLayout)
            binding.miraRecycleViewLyShimmer.miraRecycleViewLlShimmer.addView(view)
        }

        this.refreshing = refreshing
        binding.miraRecycleViewSrlRefresh.isEnabled = refreshing
        this.loadMore = loadMore
        setUpMiraRecycleView(manger)
        this.loadMore?.onInit()
    }

    fun setUp(
        manger: RecyclerView.LayoutManager,
        loadMore: LoadMoreK?
    ) {
        this.loadMore = loadMore
        setUpMiraRecycleView(manger)
//        stopLoad(0)
        this.loadMore?.onInit()
    }

    fun enabledMiraLoadMoreProgress(visibility: Int) {
        if (top) {
            binding.miraRecycleViewLyTopProgress.miraRecycleViewLlProgress.visibility = visibility
        } else {
            binding.miraRecycleViewLyBottomProgress.miraRecycleViewLlProgress.visibility =
                visibility
        }
    }

    fun enabledMiraNoLoadMoreData(visibility: Int) {
        if (top) {
            binding.miraRecycleViewLyTopProgress.miraRecycleViewLlNoMoreData.visibility = visibility
        } else {
            binding.miraRecycleViewLyBottomProgress.miraRecycleViewLlNoMoreData.visibility =
                visibility
        }
    }

    fun enabledMiraShimmerLoading(visibility: Int) {
        binding.miraRecycleViewLyShimmer.miraRecycleViewSFlLoading.visibility = visibility
        if (visibility == VISIBLE) {
            binding.miraRecycleViewLyShimmer.miraRecycleViewSFlLoading.startShimmer()
        } else {
            binding.miraRecycleViewLyShimmer.miraRecycleViewSFlLoading.stopShimmer()
        }
    }

    fun enabledMiraError(
        visibility: Int,
        errorImage: Int,
        errorText: String,
        actionText: String,
        listener: OnClickListener?
    ) {
        binding.miraRecycleViewLyError.miraRecycleViewRlError.visibility = visibility
        if (visibility == VISIBLE) {
            setMiraErrorImageIv(errorImage)
            setMiraErrorText(errorText)
            setMiraErrorAction(actionText, listener)
        }
    }

    /*
    *  type
    *      => #GIF ==> Gif Image
    *      => #OTHER ==> other Image
    * */
    fun enabledMiraError(
        visibility: Int,
        errorImage: Int,
        errorText: String,
        actionText: String,
        listener: OnClickListener?,
        type: String
    ) {
        binding.miraRecycleViewLyError.miraRecycleViewRlError.visibility = visibility
        if (visibility == VISIBLE) {
            if (type == OTHER) {
                setMiraErrorImageIv(errorImage)
            } else {
                setMiraErrorImage(errorImage)
            }
            setMiraErrorText(errorText)
            setMiraErrorAction(actionText, listener)
        }
    }

    private fun setMiraErrorImageIv(errorImage: Int) {
        binding.miraRecycleViewLyError.miraRecycleViewIvErrorImageIv.setBackgroundResource(
            errorImage
        )
    }

    private fun setMiraErrorImage(errorImage: Int) {
        binding.miraRecycleViewLyError.miraRecycleViewIvErrorImage.setImageResource(errorImage)
    }

    private fun setMiraErrorText(errorText: String) {
        binding.miraRecycleViewLyError.miraRecycleViewTvErrorText.text = (errorText)
    }

    private fun setMiraErrorAction(actionText: String, listener: OnClickListener?) {
        binding.miraRecycleViewLyError.miraRecycleViewBtnErrorAction.text = (actionText)
        binding.miraRecycleViewLyError.miraRecycleViewBtnErrorAction.setOnClickListener(listener)
    }

    private fun setMiraRecycleViewSFlShimmer(
        shimmerLayout: Int,
        countRowsShimmer: Int,
        countColumnsShimmer: Int,
    ) {
        if (shimmerLayout != 0) {
            binding.miraRecycleViewLyShimmer.miraRecycleViewLlShimmer.removeAllViews()
            if (countRowsShimmer == 1 && countColumnsShimmer == 1) {
                val view = createView(shimmerLayout)
                binding.miraRecycleViewLyShimmer.miraRecycleViewLlShimmer.addView(view)
            } else {
                for (i in 1..countRowsShimmer) {
                    if (countColumnsShimmer == 1) {
                        val view = createView(shimmerLayout)
                        binding.miraRecycleViewLyShimmer.miraRecycleViewLlShimmer.addView(view)
                    } else {
                        val linearLayout = LinearLayout(context1)
                        linearLayout.orientation = LinearLayout.HORIZONTAL
                        linearLayout.layoutParams = LinearLayout.LayoutParams(
                            LayoutParams.MATCH_PARENT,
                            LayoutParams.WRAP_CONTENT,
                            1f
                        )
                        for (j in 1..countColumnsShimmer) {
                            val view = createView(shimmerLayout)
                            linearLayout.addView(view)
                        }
                        binding.miraRecycleViewLyShimmer.miraRecycleViewLlShimmer.addView(
                            linearLayout
                        )
                    }
                }
            }
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

    private fun setUpMiraRecycleView(manger: RecyclerView.LayoutManager) {
        binding.miraRecycleViewRvRecycler.layoutManager = manger
        setPagination(manger)
        binding.miraRecycleViewSrlRefresh.setOnRefreshListener {
            reset()
            loadMore?.onReset()
            stopLoad(0)
            binding.miraRecycleViewSrlRefresh.isRefreshing = true
            enabledMiraShimmerLoading(VISIBLE)
            loadMore?.onRefresh()
        }
    }

    private fun reset() {
        onEndLess.previousTotal = 0
        onEndLess.current_page = 1
        onEndLess.previous_page = 1
        maxPage = 0
    }

    private fun setPagination(manger: RecyclerView.LayoutManager) {
        onEndLess = object : OnEndLessK(manger, 1) {
            override fun onLoadMore(current_page: Int) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page
                        stopLoad(0)
                        enabledMiraLoadMoreProgress(VISIBLE)
                        loadMore?.onLoadMore(current_page)
                    } else {
                        enabledMiraShimmerLoading(GONE)
                        onEndLess.current_page = onEndLess.previous_page
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page
                }
            }
        }
        binding.miraRecycleViewRvRecycler.addOnScrollListener(onEndLess)
    }

    fun setAdapter(adapter: RecyclerView.Adapter<*>?) {
        binding.miraRecycleViewRvRecycler.adapter = adapter
    }

    fun stopLoad(page: Int) {
        if (page > maxPage) {
            enabledMiraNoLoadMoreData(GONE)
        } else {
            if (page != 0) {
                enabledMiraNoLoadMoreData(VISIBLE)
            }
        }
        resetPresses()
    }

    private fun resetPresses() {
        enabledMiraShimmerLoading(GONE)
        enabledMiraLoadMoreProgress(GONE)
        enabledMiraError(GONE, 0, "", "", null)
        binding.miraRecycleViewSrlRefresh.isRefreshing = false
    }
}