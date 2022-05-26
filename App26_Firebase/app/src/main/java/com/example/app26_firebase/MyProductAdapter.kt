package com.example.app26_firebase

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app26_firebase.databinding.RowBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class MyProductAdapter (options: FirebaseRecyclerOptions<Product>): FirebaseRecyclerAdapter<Product, MyProductAdapter.ViewHolder>(options){

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    var itemClickListener: OnItemClickListener?= null


    inner class ViewHolder(val binding: RowBinding): RecyclerView.ViewHolder(binding.root){
        init{
            binding.root.setOnClickListener{
                itemClickListener!!.onItemClick(bindingAdapterPosition)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: MyProductAdapter.ViewHolder, position: Int, model: Product) {
        holder.binding.apply {
            productId.text = model.pId.toString()
            productName.text = model.pName
            productQuantity.text = model.pQuantity.toString()
        }
    }


}