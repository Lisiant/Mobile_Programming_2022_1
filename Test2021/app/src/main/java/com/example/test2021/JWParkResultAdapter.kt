package com.example.test2021

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.test2021.databinding.ResultRowBinding



class JWParkResultAdapter(val data: ArrayList<JWParkMyData>) : RecyclerView.Adapter<JWParkResultAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ResultRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ResultRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.resultRowTv.text = data[position].productName
    }

    override fun getItemCount(): Int {
        return data.size
    }


}