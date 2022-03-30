package com.example.app09

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    var data: ArrayList<MyData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = MyAdapter(data)
    }

    private fun initData() {
        data.add(MyData("item1", 10))
        data.add(MyData("item2", 8))
        data.add(MyData("item3", 15))
        data.add(MyData("item4", 20))
        data.add(MyData("item5", 7))
        data.add(MyData("item6", 12))
        data.add(MyData("item7", 19))
        data.add(MyData("item8", 10))
        data.add(MyData("item9", 6))
        data.add(MyData("item10", 20))
        data.add(MyData("item11", 9))
        data.add(MyData("item12", 13))
    }
}