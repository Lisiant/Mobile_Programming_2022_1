package com.example.app10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    val data: ArrayList<MyData> = ArrayList()
    lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter = MyAdapter(data)
        adapter.itemClickListener = object : MyAdapter.OnItemClickListener {
            override fun OnItemClick(data: MyData) {
                //this하면 익명 클래스이므로 안됨, applicationContext를 통해 어디서든 context 정보를 얻을 수 있음.
                Toast.makeText(applicationContext, data.meaning, Toast.LENGTH_SHORT).show()
            }
        }
        recyclerView.adapter = adapter


    }

    private fun initData() {
        val scan = Scanner(resources.openRawResource(R.raw.words))
        while (scan.hasNextLine()) {
            val word = scan.nextLine()
            val meaning = scan.nextLine()
            data.add(MyData(word, meaning))

        }
    }
}