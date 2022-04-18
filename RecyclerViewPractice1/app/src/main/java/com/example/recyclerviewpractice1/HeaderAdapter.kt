package com.example.recyclerviewpractice1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpractice1.databinding.HeaderItemBinding

class HeaderAdapter : RecyclerView.Adapter<HeaderAdapter.HeaderViewHolder>() {
    lateinit var binding: HeaderItemBinding
    private var flowerCount: Int = 0

    inner class HeaderViewHolder(val binding: HeaderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(flowerCount: Int) {
            binding.flowerNumberText.text = flowerCount.toString()
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HeaderAdapter.HeaderViewHolder {
        val binding = HeaderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HeaderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HeaderAdapter.HeaderViewHolder, position: Int) {
        holder.bind(flowerCount)
    }

    override fun getItemCount(): Int {
        return 1
    }

    fun updateFlowerCount(updatedFlowerCount: Int){
        flowerCount = updatedFlowerCount
        notifyDataSetChanged()
    }
}