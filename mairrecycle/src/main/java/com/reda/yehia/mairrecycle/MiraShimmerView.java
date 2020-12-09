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

import com.facebook.shimmer.ShimmerFrameLayout;
import com.reda.yehia.mairrecycle.databinding.MiraShimmerLayoutBinding;

/*
 * Yehia Reda
 * */

public class MiraShimmerView extends RelativeLayout {

    private Context context;
    private LayoutInflater inflter;
    private ShimmerFrameLayout shimmerFrameLayout;
    private MiraShimmerLayoutBinding binding;

    public MiraShimmerView(Context context) {
        super(context);
        this.context = context;
    }

    public MiraShimmerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public MiraShimmerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MiraShimmerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;

    }

    public void initView(Integer shimmerLayout, int countRowsShimmer, int countColumnsShimmer, int orientation) {
        inflter = (LayoutInflater.from(context));
        binding = DataBindingUtil.inflate(
                inflter, R.layout.mira_shimmer_layout, this, false);
        setMiraRecycleViewSFlShimmer(shimmerLayout, countRowsShimmer, countColumnsShimmer, orientation);
    }

    private void setMiraRecycleViewSFlShimmer(Integer shimmerLayout, int countRowsShimmer, int countColumnsShimmer, int orientation) {

        binding.miraRecycleViewSFlShimmer.removeAllViews();
        binding.miraRecycleViewSFlShimmer.setOrientation(orientation);

        if (orientation == RecyclerView.HORIZONTAL) {

            binding.miraRecycleViewSFlHorizontalShimmer.setVisibility(VISIBLE);
            binding.miraRecycleViewSSvShimmer.setVisibility(GONE);

            for (int j = 0; j < countColumnsShimmer; j++) {
                View view = inflate(context, shimmerLayout, null);
                view.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
                binding.miraRecycleViewSFlHorizontalShimmer.addView(view);
            }
        } else {

            binding.miraRecycleViewSSvShimmer.setVisibility(VISIBLE);
            binding.miraRecycleViewSFlHorizontalShimmer.setVisibility(GONE);

            for (int i = 0; i <= countRowsShimmer; i++) {
                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));

                for (int j = 0; j < countColumnsShimmer; j++) {
                    View view = inflate(context, shimmerLayout, null);
                    view.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
                    linearLayout.addView(view);
                }

                binding.miraRecycleViewSFlShimmer.addView(linearLayout);
            }
        }

    }


}
