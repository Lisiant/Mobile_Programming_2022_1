package com.example.test2021

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test2021.databinding.RowBinding

class JWParkAdapter(val items: ArrayList<JWParkMyData>) : RecyclerView.Adapter<JWParkAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(data: JWParkMyData, position: Int)
    }

    var itemClickListener: OnItemClickListener? = null


    inner class ViewHolder(val binding: RowBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.productName.setOnClickListener {
                itemClickListener?.onItemClick(items[adapterPosition], adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.productName.text =  "제품명: " + items[position].productName
        holder.binding.productPrice.text = "제품가격: " + items[position].productPrice

    }


    override fun getItemCount(): Int {
        return items.size
    }


}