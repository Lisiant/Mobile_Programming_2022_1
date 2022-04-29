package com.example.jwparkmidtest

import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.jwparkmidtest.databinding.RowBinding

class MyAdapter (val items: ArrayList<MyData>) : RecyclerView.Adapter<MyAdapter.ViewHolder>(){

    interface OnItemClickListener {
        fun onItemClick(data: MyData, position: Int)
    }

    var itemClickListener: OnItemClickListener? = null

    inner class ViewHolder(val binding: RowBinding):RecyclerView.ViewHolder(binding.root) {
        init {
            binding.imageView.setOnClickListener {
                itemClickListener?.onItemClick(items[adapterPosition],adapterPosition)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.rowNameTv.text = items[position].name
        holder.binding.rowCompanyNameTv.text = items[position].companyName
        holder.binding.rowPhoneNumberTv.text = items[position].phoneNumber
        holder.binding.rowButton.text = items[position].count.toString()


    }

    override fun getItemCount(): Int {
        return items.size
    }


}