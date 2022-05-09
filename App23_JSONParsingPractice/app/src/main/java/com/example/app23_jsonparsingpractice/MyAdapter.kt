package com.example.app23_jsonparsingpractice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app23_jsonparsingpractice.databinding.RowBinding

class MyAdapter(val items: ArrayList<MyData>):RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: RowBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.textView.text = items[position].nickName
    }

    override fun getItemCount(): Int {
        return items.size
    }
}