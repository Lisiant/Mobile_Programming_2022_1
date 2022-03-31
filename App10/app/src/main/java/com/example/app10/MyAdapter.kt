package com.example.app10

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val items: ArrayList<MyData>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    // 인터페이스 정의: 어댑터 클래스에서 하기
    interface OnItemClickListener {
        fun OnItemClick(data: MyData)
    }

    var itemClickListener: OnItemClickListener? = null


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView = itemView.findViewById<TextView>(R.id.textView)

        //누군가 리스너 구현 -> 이벤트 호출이 되면 텍스트뷰가 이벤트 핸들링을 할 수 있게 텍스트뷰에 리스너를 달아준다.
        //텍스트뷰가 클릭되면 우리가 구현했던 인터페이스의 메서드인 OnItemClick를 실행.
        // OnItemClick의 인자로는 MyData ArrayList인 items의 포지션에 해당되는 위치의 내용을 부여.
        init {
            textView.setOnClickListener {
                itemClickListener?.OnItemClick(items[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = items[position].word
    }

    override fun getItemCount(): Int {
        return items.size
    }


}