package com.example.app10

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app10.databinding.RowBinding

class MyAdapter(val items: ArrayList<MyData>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    // 인터페이스 정의: 어댑터 클래스에서 하기
    interface OnItemClickListener {
        fun OnItemClick(data: MyData, meaningTextView: TextView)
    }

    fun moveItem(oldPos: Int, newPos: Int) {
        // 데이터 순서 변경
        val item = items[oldPos]
        items.removeAt(oldPos)
        items.add(newPos, item)
        // 변경된 데이터가 있다고 알려주어야 한다: 알려주는 메서드는 notifyItemMoved
        // 변경된 데이터가 있으면 RecyclerView를 갱신
        notifyItemMoved(oldPos, newPos)
    }

    fun removeItem(pos: Int) {
        //데이터 삭제
        items.removeAt(pos)
        notifyItemRemoved(pos)
    }


    var itemClickListener: OnItemClickListener? = null


    //누군가 리스너 구현 -> 이벤트 호출이 되면 텍스트뷰가 이벤트 핸들링을 할 수 있게 텍스트뷰에 리스너를 달아준다.
    //텍스트뷰가 클릭되면 우리가 구현했던 인터페이스의 메서드인 OnItemClick를 실행.
    // OnItemClick의 인자로는 MyData ArrayList인 items의 포지션에 해당되는 위치의 내용을 부여.

    // ViewHolder의 인자로 binding을 받고, 상속받은 클래스의 인자로는 view를 넣어주어야 하므로 binding.root를 넣어 준다.
    //
    inner class ViewHolder(val binding: RowBinding) : RecyclerView.ViewHolder(binding.root) {
        //val textView = itemView.findViewById<TextView>(R.id.textView)
        //val meaningTextView = itemView.findViewById<TextView>(R.id.meaning_tv)


        init {
            binding.textView.setOnClickListener {
                itemClickListener?.OnItemClick(items[adapterPosition], binding.meaningTv)
            }
        }
    }

    //binding 사용 시 onCreateViewHolder에서 view를 넘겨주고, row에 대한 id를 대체하기 위해 여기서 binding 변수를 설정해준다.
    //ViewHolder에는 binding을 넘겨주고, 그로 인해 inner class ViewHolder 부분도 바꾸어준다.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textView.text = items[position].word
        holder.binding.meaningTv.text = items[position].meaning
        holder.binding.meaningTv.visibility = View.GONE

        if (!items[position].isOpened) {
            holder.binding.meaningTv.visibility = View.GONE
        } else {
            holder.binding.meaningTv.visibility = View.VISIBLE
        }


    }


    override fun getItemCount(): Int {
        return items.size
    }


}