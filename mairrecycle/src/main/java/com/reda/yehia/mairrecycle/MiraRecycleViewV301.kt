package com.reda.yehia.mairrecycle

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.reda.yehia.mairrecycle.databinding.MiraRecycleViewV31LayoutBinding

class MiraRecycleViewV301 : RelativeLayout {

    private var errorImage: Int = 0
    private var errorImageTypeTxt: String = ""
    private var errorText: String = ""
    private var actionText: String = ""
    val GIF = "GIF"
    val OTHER = "OTHER"

    var context1: Context
    lateinit var inflter: LayoutInflater
    lateinit var onEndLess: OnEndLessK
    lateinit var binding: MiraRecycleViewV31LayoutBinding

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
            attrs,
            defStyleAttr
        )
    }

    private fun initView() {
        inflter = LayoutInflater.from(context1)
        if (!::binding.isInitialized) {
            binding = MiraRecycleViewV31LayoutBinding.inflate(inflter, parent as ViewGroup?, false)
        }
        addView(binding.root)
    }

    @SuppressLint("Recycle", "CustomViewStyleable")
    private fun initView(attrs: AttributeSet, defStyleAttr: Int) {
        inflter = LayoutInflater.from(context1)
        if (!::binding.isInitialized) {
            binding = MiraRecycleViewV31LayoutBinding.inflate(inflter, parent as ViewGroup?, false)
            setAttributes(attrs, defStyleAttr)
        }
        addView(binding.root)
    }

    @SuppressLint("Recycle", "CustomViewStyleable")
    private fun setAttributes(attrs: AttributeSet, defStyleAttr: Int) {

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.MiraRecycleViewV3, defStyleAttr, 0
        )

        val shimmerLayout =
            typedArray.getResourceId(R.styleable.MiraRecycleViewV3_mira_shimmer_layout, 0)
        var visibility =
            typedArray.getInt(R.styleable.MiraRecycleViewV3_mira_visibility, View.VISIBLE)
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
        val refreshing =
            typedArray.getBoolean(R.styleable.MiraRecycleViewV3_mira_refreshing, true)
        val attrsEnabled =
            typedArray.getBoolean(R.styleable.MiraRecycleViewV3_mira_attrs_enabled, false)
        errorImage = typedArray.getResourceId(R.styleable.MiraRecycleViewV3_mira_error_image, 0)
        val errorImageType =
            typedArray.getInt(R.styleable.MiraRecycleViewV3_mira_error_image_type, 0)
        errorText = typedArray.getString(R.styleable.MiraRecycleViewV3_mira_error_message)!!
        if (typedArray.getString(R.styleable.MiraRecycleViewV3_mira_error_title) != null) {
            actionText = typedArray.getString(R.styleable.MiraRecycleViewV3_mira_error_title)!!
        }

        if (attrsEnabled) {
            if (shimmerLayout != 0) {
                setMiraRecycleViewSFlShimmer(
                    shimmerLayout
                )
                enabledMiraShimmerLoading(visibility)
            }
            this.refreshing = refreshing
            binding.miraRecycleViewSrlRefresh.isEnabled = refreshing

            if (errorText.isEmpty()) {
                errorText = ""
            }
            if (actionText.isEmpty()) {
                actionText = ""
            }
            errorImageTypeTxt = if (errorImageType == 0) {
                OTHER
            } else {
                GIF
            }

            enabledMiraError(
                GONE,
                errorImage,
                errorText,
                actionText,
                null,
                errorImageTypeTxt
            )
        }
    }

    fun setUp(
        shimmerLayout: Int,
        manger: RecyclerView.LayoutManager,
        refreshing: Boolean,
        loadMore: LoadMoreK?
    ) {
        setMiraRecycleViewSFlShimmer(
            shimmerLayout
        )
        enabledMiraShimmerLoading(visibility)

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

    private fun enabledMiraNoLoadMoreData(visibility: Int) {
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
        visibility: Int?,
        listener: OnClickListener?
    ) {
        binding.miraRecycleViewLyError.miraRecycleViewRlError.visibility = visibility!!
        if (listener != null) {
            binding.miraRecycleViewLyError.miraRecycleViewBtnErrorAction.setOnClickListener(listener)
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

    fun enabledMiraError(
        visibility: Int,
        listener: OnClickListener?
    ) {
        binding.miraRecycleViewLyError.miraRecycleViewRlError.visibility = visibility
        enabledMiraError(
            visibility,
            errorImage,
            errorText,
            actionText,
            listener,
            errorImageTypeTxt
        )
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
        binding.miraRecycleViewLyError.miraRecycleViewBtnErrorAction.visibility = VISIBLE
        if (actionText != "") {
            binding.miraRecycleViewLyError.miraRecycleViewBtnErrorAction.text = (actionText)
        }
        binding.miraRecycleViewLyError.miraRecycleViewBtnErrorAction.setOnClickListener(listener)
    }

    private fun setMiraRecycleViewSFlShimmer(
        shimmerLayout: Int,
    ) {
        if (shimmerLayout != 0) {
            binding.miraRecycleViewLyShimmer.miraRecycleViewTvShimmer.setImageResource(shimmerLayout)
        }
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
        enabledMiraError(GONE, null)
        maxPage = 0
        binding.miraRecycleViewSrlRefresh.isRefreshing = false
    }
}