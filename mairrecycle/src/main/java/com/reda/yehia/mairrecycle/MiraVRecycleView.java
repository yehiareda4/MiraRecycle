package com.reda.yehia.mairrecycle;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.reda.yehia.mairrecycle.databinding.MiraV2RecycleViewLayoutBinding;

public class MiraVRecycleView extends RelativeLayout {

    private Context context;
    private OnEndLess onEndLess;
    public int maxPage = 0;

    public LoadMore loadMore;
    public int orientation = LinearLayout.VERTICAL;
    private LayoutInflater inflter;
    public MiraV2RecycleViewLayoutBinding binding;
    private boolean refreshing = true;

    public MiraVRecycleView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public MiraVRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public MiraVRecycleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MiraVRecycleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        initView();
    }

    private void initView() {
        inflter = (LayoutInflater.from(context));
        binding = DataBindingUtil.inflate(
                inflter, R.layout.mira_v2_recycle_view_layout, this, false);

        addView(binding.getRoot());
    }

    public void setUp(Integer shimmerLayout, int countRowsShimmer, int countColumnsShimmer, RecyclerView.LayoutManager manger, int orientation
            , boolean refreshing, LoadMore loadMore) {
        setMiraRecycleViewSFlShimmer(shimmerLayout, countRowsShimmer, countColumnsShimmer, orientation);

        this.refreshing = refreshing;
        binding.miraRecycleViewSrlRefresh.setEnabled(refreshing);

        this.loadMore = loadMore;

        setUpMiraRecycleView(manger);
    }

    public void enabledMiraLoadMoreProgress(int visibility) {
        binding.miraRecycleViewLlProgress.setVisibility(visibility);
    }

    public void enabledMiraNoLoadMoreData(int visibility) {
        binding.miraRecycleViewLlNoMoreData.setVisibility(visibility);
    }

    public void enabledMiraShimmerLoading(int visibility) {
        binding.miraRecycleViewLlLoading.setVisibility(visibility);

        if (visibility == VISIBLE) {
            binding.miraRecycleViewSFlLoading.startShimmer();
        } else {
            binding.miraRecycleViewSFlLoading.stopShimmer();
        }
    }

    public void enabledMiraError(int visibility, int errorImage, String errorText, String actionText, OnClickListener listener) {
        binding.miraRecycleViewRlError.setVisibility(visibility);

        if (visibility == View.VISIBLE) {
            setMiraErrorImage(errorImage);
            setMiraErrorText(errorText);
            setMiraErrorAction(actionText, listener);
        }
    }

    private void setMiraErrorImage(int errorImage) {
        binding.miraRecycleViewIvErrorImage.setImageResource(errorImage);
    }

    private void setMiraErrorText(String errorText) {
        binding.miraRecycleViewTvErrorText.setText(errorText);
    }

    private void setMiraErrorAction(String actionText, OnClickListener listener) {
        binding.miraRecycleViewBtnErrorAction.setText(actionText);
        binding.miraRecycleViewBtnErrorAction.setOnClickListener(listener);
    }

    private void setMiraRecycleViewSFlShimmer(Integer shimmerLayout, int countRowsShimmer, int countColumnsShimmer, int orientation) {
        if (shimmerLayout != 0) {

            binding.miraRecycleViewSFlShimmer.removeAllViews();
            binding.miraRecycleViewSFlShimmerHorizontal.removeAllViews();

            if (orientation == LinearLayout.HORIZONTAL) {
                for (int j = 0; j < countColumnsShimmer; j++) {
                    View view = inflate(context, shimmerLayout, null);
                    view.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f));
                    binding.miraRecycleViewSFlShimmerHorizontal.addView(view);
                }
            } else {
                for (int i = 1; i <= countRowsShimmer; i++) {
                    LinearLayout linearLayout = new LinearLayout(getContext());
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f));

                    for (int j = 0; j < countColumnsShimmer; j++) {
                        View view = inflate(context, shimmerLayout, null);
                        view.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f));
                        linearLayout.addView(view);
                    }

                    binding.miraRecycleViewSFlShimmer.addView(linearLayout);
                }
            }
            enabledMiraShimmerLoading(View.VISIBLE);
        }
    }

    private void setUpMiraRecycleView(RecyclerView.LayoutManager manger) {
        binding.miraRecycleViewRvRecycler.setLayoutManager(manger);
        setPagination(manger);

        binding.miraRecycleViewSrlRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reset();
                loadMore.onReset();
                stopLoad(0);
                binding.miraRecycleViewSrlRefresh.setRefreshing(true);
                enabledMiraShimmerLoading(View.VISIBLE);
                loadMore.onRefresh();
            }
        });
    }

    public void reset() {
        onEndLess.previousTotal = 0;
        onEndLess.current_page = 1;
        onEndLess.previous_page = 1;
        maxPage = 0;
    }

    private void setPagination(RecyclerView.LayoutManager manger) {
        onEndLess = new OnEndLess(manger, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        enabledMiraLoadMoreProgress(View.VISIBLE);
                        loadMore.onLoadMore(current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }
            }
        };
        binding.miraRecycleViewRvRecycler.addOnScrollListener(onEndLess);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        binding.miraRecycleViewRvRecycler.setAdapter(adapter);
    }

    public void stopLoad(int page) {
        enabledMiraLoadMoreProgress(View.GONE);
        if (page > maxPage) {
            enabledMiraNoLoadMoreData(View.GONE);
        } else {
            if (page != 0) {
                enabledMiraNoLoadMoreData(View.VISIBLE);
            }
        }
        enabledMiraShimmerLoading(View.GONE);
        enabledMiraError(View.GONE, 0, "", "", null);
        binding.miraRecycleViewSrlRefresh.setRefreshing(false);
    }
}
