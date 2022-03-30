package com.example.app09

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(val items: ArrayList<MyData>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    // ViewHolder -> 뷰에 관한 정보를 가지고 있는 클래스. itemView에는 row.xml의 LinearLayout을 객체화한 뷰 정보가 들어간다.
    // ViewHolder 클래스에는 데이터가 연결되어서 변경되어야 하는 멤버를 만들어야 한다.
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // itemView 내부의 textView를 찾겠다.
        val textView = itemView.findViewById<TextView>(R.id.textView)
    }

    // ViewHolder가 필요할 때마다 호출되는 메서드. ViewHolder를 만들어서 반환하는 역할.
    // ViewHolder는 정보를 가지고 있어야 한다. -> row.xml을 객체화하는 역할
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // 객체화할 때는 LayoutInfalter으로만 가능. context 정보를 가지고 있는 곳에서만(= parent) LayoutInflater 객체를 획득 가능.
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return ViewHolder(view)
    }

    //뷰홀더가 가진 멤버들에 우리가 가진 데이터 중 포지션에 해당하는 위치를 뷰홀더에 바인딩.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = items[position].textString
        holder.textView.textSize = items[position].textPt.toFloat()
    }

    override fun getItemCount(): Int {
        return items.size
    }


}