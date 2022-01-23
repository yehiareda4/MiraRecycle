package com.yehia.mira_recycle.view.activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.reda.yehia.mairrecycle.LoadMoreK
import com.yehia.mira_recycle.R
import com.yehia.mira_recycle.adapter.MiraRecyclerAdapter
import com.yehia.mira_recycle.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding
    lateinit var adapter: MiraRecyclerAdapter
    lateinit var dataList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.miraRecycleViews.setBackgroundResource(R.color.black)

        binding.miraRecycleView.setUp(
            LinearLayoutManager(this),
            object : LoadMoreK() {
                override fun onLoadMore(current_page: Int) {
                    if (current_page > 1) {
                        setData()
                    }
                }

                override fun onRefresh() {
                    setData()
                }

                override fun onReset() {
                    dataList.clear()
                    adapter = MiraRecyclerAdapter(this@SplashActivity, dataList)
                    binding.miraRecycleView.setAdapter(adapter)
                }

                override fun onInit() {
                    dataList = ArrayList()
                    adapter = MiraRecyclerAdapter(this@SplashActivity, dataList)
                    binding.miraRecycleView.binding.miraRecycleViewRvRecycler.layoutAnimation =
                        AnimationUtils.loadLayoutAnimation(
                            this@SplashActivity,
                            com.reda.yehia.mairrecycle.R.anim.layout_animation_from_scale
                        )
                    binding.miraRecycleView.setAdapter(adapter)
                }
            }
        )
        binding.miraRecycleView.enabledMiraShimmerLoading(View.VISIBLE)
        setData()
    }

    private fun setData() {
        Handler(Looper.getMainLooper()).postDelayed({
            binding.miraRecycleViews.setBackgroundResource(R.color.colorPrimary)
            binding.miraRecycleView.stopLoad(0)
            addData(dataList)
            adapter.notifyDataSetChanged()

            binding.miraRecycleView.scheduleLayoutAnimation();
            binding.miraRecycleView.maxPage = 4

        }, 5000)
    }

    private fun addData(dataList: java.util.ArrayList<String>) {
        dataList.add("auodgh")
        dataList.add("auodgh")
        dataList.add("auodgh")
        dataList.add("auodgh")
        dataList.add("auodgh")
        dataList.add("auodgh")
        dataList.add("auodgh")
        dataList.add("auodgh")
        dataList.add("auodgh")
        dataList.add("auodgh")
        dataList.add("auodgh")
        dataList.add("auodgh")
        dataList.add("auodgh")
    }

}