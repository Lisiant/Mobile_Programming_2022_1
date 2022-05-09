package com.example.app23_jsonparsingpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app23_jsonparsingpractice.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.jsoup.Jsoup

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: MyAdapter
    val url = "https://nickname.hwanmoo.kr/?format=json&count=20"
    val scope = CoroutineScope(Dispatchers.IO)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }


    private fun init() {
        binding.swipe.setOnRefreshListener {
            binding.swipe.isRefreshing = true
            getNickName()
        }
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
        adapter = MyAdapter(ArrayList())
        binding.recyclerView.adapter = adapter
        getNickName()
    }

    private fun getNickName() {
        adapter.items.clear()
        scope.launch {
            val doc = Jsoup.connect(url).ignoreContentType(true).get()
            val json = JSONObject(doc.text())
            var data = json.getString("words")
            data = data.slice(IntRange(1, data.length - 2))
            val nickNames = data.split(",")
            for (item in nickNames) {
                adapter.items.add(MyData(item.slice(IntRange(1, item.length - 2))))

            }
            withContext(Dispatchers.Main) {
                adapter.notifyDataSetChanged()
                binding.swipe.isRefreshing = false
            }
        }


    }
}