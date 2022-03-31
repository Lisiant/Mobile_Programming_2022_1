package com.example.app09

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class MainActivity : AppCompatActivity() {

    var data: ArrayList<MyData> = ArrayList()
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initData()
        initRecyclerView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu1, menu)
        return true
        // return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuItem1 -> {
                recyclerView.layoutManager =
                    LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            }
            R.id.menuItem2 -> {
                recyclerView.layoutManager = GridLayoutManager(this, 3)
            }
            R.id.menuItem3 -> {
                recyclerView.layoutManager =
                    StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            }
        }

        return super.onOptionsItemSelected(item)

    }

    private fun initRecyclerView() {
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
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