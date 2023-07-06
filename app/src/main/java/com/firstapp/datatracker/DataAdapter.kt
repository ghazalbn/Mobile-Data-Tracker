package com.firstapp.datatracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firstapp.datatracker.databinding.ItemDataUsageBinding

class DataAdapter(private val dataUsages: List<DataModel>) :
    RecyclerView.Adapter<DataAdapter.DataUsageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataUsageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val modelIndexProductBinding: ItemDataUsageBinding =
            ItemDataUsageBinding.inflate(layoutInflater, parent, false)
        return DataUsageViewHolder(modelIndexProductBinding)
    }

    override fun getItemCount(): Int {
        return dataUsages.size
    }

    override fun onBindViewHolder(holder: DataUsageViewHolder, position: Int) {
        holder.bind(dataUsages[position])
    }

    inner class DataUsageViewHolder(var binding: ItemDataUsageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataModel) {
            binding.mobileData.text = data.mobile
            binding.tvDate.text = data.date
        }
    }
}