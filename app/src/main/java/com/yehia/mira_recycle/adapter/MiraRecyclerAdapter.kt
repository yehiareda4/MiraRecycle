package com.yehia.mira_recycle.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yehia.mira_recycle.databinding.ItemTestBinding

class MiraRecyclerAdapter(private val context: Context, private val dataList: ArrayList<String>) :
    RecyclerView.Adapter<MiraRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)


        val binding = ItemTestBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    class ViewHolder(val binding: ItemTestBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(orderViewModel: ItemTestBinding) {
            binding.executePendingBindings()
        }

    }
}
