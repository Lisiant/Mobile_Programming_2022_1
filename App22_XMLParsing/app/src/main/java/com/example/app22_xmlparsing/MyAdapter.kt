package com.example.app22_xmlparsing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app22_xmlparsing.databinding.RowBinding

class MyAdapter(val items: ArrayList<MyData>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    var itemClickListener: OnItemClickListener? = null

    inner class MyViewHolder(val binding: RowBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.newsTitle.setOnClickListener {
                itemClickListener?.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyViewHolder {
        val view = RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyAdapter.MyViewHolder, position: Int) {
        holder.binding.newsTitle.text = items[position].newsTitle
    }

    override fun getItemCount(): Int {
        return items.size
    }


}