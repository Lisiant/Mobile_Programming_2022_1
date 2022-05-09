package com.example.app21_webparsing

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app21_webparsing.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MyAdapter
    val url = "https://news.daum.net"
    val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun getNews() {
        adapter.items.clear()
        scope.launch {
            val doc = Jsoup.connect(url).get()
            val headlines = doc.select("ul.list_newsissue > li > div.item_issue > div > strong.tit_g > a")
            for (news in headlines){
                adapter.items.add(MyData(news.text(), news.absUrl("href")))
            }
            withContext(Dispatchers.Main) {
                adapter.notifyDataSetChanged()
                binding.swipe.isRefreshing = false
            }
        }
    }

    private fun init() {
        binding.swipe.setOnRefreshListener {
            binding.swipe.isRefreshing = true
            getNews()
        }
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        adapter = MyAdapter(ArrayList<MyData>())
        adapter.itemClickListener = object : MyAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(ACTION_VIEW, Uri.parse(adapter.items[position].url))
                startActivity(intent)
            }
        }
        binding.recyclerView.adapter= adapter
        getNews()

    }
}
