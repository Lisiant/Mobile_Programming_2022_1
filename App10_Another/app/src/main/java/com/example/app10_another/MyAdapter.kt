package com.example.app10_another

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val items:ArrayList<MyData>) :RecyclerView.Adapter<MyAdapter.ViewHolder>(){
    interface OnItemClickListner{
        fun OnItemClick(data:MyData , position: Int)
    }
    fun moveItem(oldPos:Int, newPos:Int){           //drag&drop
        val item = items[oldPos]
        val item2 = items[newPos]
        items.removeAt(oldPos)
        items.add(newPos,item)
        notifyItemMoved(oldPos,newPos)
    }
    fun removeItem(pos:Int){                        //swipe(삭제)
        items.removeAt(pos)
        notifyItemRemoved(pos)
    }

    var itemClickListner: OnItemClickListner?= null
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView = itemView.findViewById<TextView>(R.id.textView)
        val textView2 = itemView.findViewById<TextView>(R.id.textView2)
        init{
            textView.setOnClickListener{
                itemClickListner?.OnItemClick(items[adapterPosition] , adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = items[position].word
        holder.textView2.text = items[position].meaning
        holder.textView2.visibility = View.GONE
        if(items[position].isClicked)
            holder.textView2.visibility = View.VISIBLE
        else
            holder.textView2.visibility = View.GONE
    }

    override fun getItemCount(): Int {
        return items.size
    }

}
