package com.reda.yehia.mairrecycle

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.reda.yehia.mairrecycle.databinding.MiraVkRecycleViewLayoutBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MiraVKRecycleView : RelativeLayout {

    val GIF = "GIF"
    val OTHER = "OTHER"

    var context1: Context
    lateinit var inflter: LayoutInflater
    lateinit var onEndLess: OnEndLessK
    lateinit var binding: MiraVkRecycleViewLayoutBinding

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

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
        initView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        this.context1 = context
        initView()
    }

    private fun initView() {
        inflter = LayoutInflater.from(context1)
        binding = DataBindingUtil.inflate(
            inflter, R.layout.mira_vk_recycle_view_layout, this, false
        )
        addView(binding.getRoot())
    }

    fun setUp(
        shimmerLayout: Int,
        countRowsShimmer: Int,
        countColumnsShimmer: Int,
        manger: RecyclerView.LayoutManager,
        orientation: Int,
        refreshing: Boolean,
        loadMore: LoadMoreK?
    ) {
        setMiraRecycleViewSFlShimmer(
            shimmerLayout,
            countRowsShimmer,
            countColumnsShimmer,
            orientation
        )
        this.refreshing = refreshing
        binding.miraRecycleViewSrlRefresh.isEnabled = refreshing
        this.loadMore = loadMore
        setUpMiraRecycleView(manger)
        this.loadMore?.onInit()
    }

    fun setUp(
        shimmerLayout: Int,
        manger: RecyclerView.LayoutManager,
        refreshing: Boolean,
        loadMore: LoadMoreK?
    ) {
        binding.miraRecycleViewSFlShimmer.removeAllViews()
        val view = inflate(context1, shimmerLayout, null)
        view.layoutParams = LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.MATCH_PARENT,
            1f
        )
        binding.miraRecycleViewSFlShimmer.addView(view)

        this.refreshing = refreshing
        binding.miraRecycleViewSrlRefresh.isEnabled = refreshing
        this.loadMore = loadMore
        setUpMiraRecycleView(manger)
        this.loadMore?.onInit()
    }

    fun enabledMiraLoadMoreProgress(visibility: Int) {
        if (top) {
            binding.miraRecycleViewLlTopProgress.visibility = visibility
        } else {
            binding.miraRecycleViewLlProgress.visibility = visibility
        }
    }

    fun enabledMiraNoLoadMoreData(visibility: Int) {
        if (top) {
            binding.miraRecycleViewLlTopNoMoreData.visibility = visibility
        } else {
            binding.miraRecycleViewLlNoMoreData.visibility = visibility
        }
    }

    fun enabledMiraShimmerLoading(visibility: Int) {
        binding.miraRecycleViewLlLoading.visibility = visibility
        if (visibility == VISIBLE) {
            binding.miraRecycleViewSFlLoading.startShimmer()
        } else {
            binding.miraRecycleViewSFlLoading.stopShimmer()
        }
    }

    fun enabledMiraError(
        visibility: Int,
        errorImage: Int,
        errorText: String,
        actionText: String,
        listener: OnClickListener?
    ) {
        binding.miraRecycleViewRlError.visibility = visibility
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
        binding.miraRecycleViewRlError.visibility = visibility
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
        binding.miraRecycleViewIvErrorImageIv.setBackgroundResource(errorImage)
    }

    private fun setMiraErrorImage(errorImage: Int) {
        binding.miraRecycleViewIvErrorImage.setImageResource(errorImage)
    }

    private fun setMiraErrorText(errorText: String) {
        binding.miraRecycleViewTvErrorText.setText(errorText)
    }

    private fun setMiraErrorAction(actionText: String, listener: OnClickListener?) {
        binding.miraRecycleViewBtnErrorAction.setText(actionText)
        binding.miraRecycleViewBtnErrorAction.setOnClickListener(listener)
    }

    private fun setMiraRecycleViewSFlShimmer(
        shimmerLayout: Int,
        countRowsShimmer: Int,
        countColumnsShimmer: Int,
        orientation: Int
    ) {
        coroutineScope.launch {

            if (shimmerLayout != 0) {
                binding.miraRecycleViewSFlShimmer.removeAllViews()
                binding.miraRecycleViewSFlShimmerHorizontal.removeAllViews()
                if (orientation == LinearLayout.HORIZONTAL) {
                    for (j in 1..countColumnsShimmer) {
                        val view = inflate(context1, shimmerLayout, null)
                        view.layoutParams = LinearLayout.LayoutParams(
                            LayoutParams.MATCH_PARENT,
                            LayoutParams.MATCH_PARENT,
                            1f
                        )
                        binding.miraRecycleViewSFlShimmerHorizontal.addView(view)
                    }
                } else {
                    for (i in 1..countRowsShimmer) {
                        val linearLayout = LinearLayout(context1)
                        linearLayout.orientation = LinearLayout.HORIZONTAL
                        linearLayout.layoutParams = LinearLayout.LayoutParams(
                            LayoutParams.MATCH_PARENT,
                            LayoutParams.WRAP_CONTENT,
                            1f
                        )
                        for (j in 1..countColumnsShimmer) {
                            val view = inflate(context1, shimmerLayout, null)
                            view.layoutParams = LinearLayout.LayoutParams(
                                LayoutParams.MATCH_PARENT,
                                LayoutParams.WRAP_CONTENT,
                                1f
                            )
                            linearLayout.addView(view)
                        }
                        binding.miraRecycleViewSFlShimmer.addView(linearLayout)
                    }
                }
            }

        }
    }

    private fun setUpMiraRecycleView(manger: RecyclerView.LayoutManager) {
        binding.miraRecycleViewRvRecycler.layoutManager = manger
        setPagination(manger)
        binding.miraRecycleViewSrlRefresh.setOnRefreshListener {
            reset()
            loadMore?.onReset()
            stopLoad(0)
            binding.miraRecycleViewSrlRefresh.setRefreshing(true)
            enabledMiraShimmerLoading(VISIBLE)
            loadMore?.onRefresh()
        }
    }

    fun reset() {
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
                        enabledMiraLoadMoreProgress(VISIBLE)
                        enabledMiraNoLoadMoreData(GONE)
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
        resetProsses()
    }

    fun resetProsses() {
        enabledMiraShimmerLoading(GONE)
        enabledMiraLoadMoreProgress(GONE)
        enabledMiraError(GONE, 0, "", "", null)
        binding.miraRecycleViewSrlRefresh.isRefreshing = false
    }
}