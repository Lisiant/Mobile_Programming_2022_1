package com.example.app22_xmlparsing

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app22_xmlparsing.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.parser.Parser

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MyAdapter
    val url = "https://news.daum.net"
    val scope = CoroutineScope(Dispatchers.IO)
    val rssurl = "http://fs.jtbc.joins.com//RSS/culture.xml"
    val jsonurl = "http://api.icndb.com/jokes/random"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        getJSON()
    }


    private fun getNews() {
        adapter.items.clear()
        scope.launch {
            val doc = Jsoup.connect(url).get()
            val headlines =
                doc.select("ul.list_newsissue > li > div.item_issue > div > strong.tit_g > a")
            for (news in headlines) {
                adapter.items.add(MyData(news.text(), news.absUrl("href")))
            }
            withContext(Dispatchers.Main) {
                adapter.notifyDataSetChanged()
                binding.swipe.isRefreshing = false
            }
        }
    }


    private fun getRssNews() {
        adapter.items.clear()
        scope.launch {
            val doc = Jsoup.connect(rssurl).parser(Parser.xmlParser()).get()
            val headlines = doc.select("item")
            for (news in headlines) {
                adapter.items.add(MyData(news.select("title").text(), news.select("link").text()))
            }
            withContext(Dispatchers.Main) {
                adapter.notifyDataSetChanged()
                binding.swipe.isRefreshing = false
            }
        }
    }

    private fun getJSON() {
        scope.launch {
            val doc = Jsoup.connect(jsonurl).ignoreContentType(true).get()
            Log.i("joke", doc.text())
            val json = JSONObject(doc.text())
            val joke  = json.getJSONObject("value")
            val jokestr = joke.getString("joke")
            Log.i("jokestr", jokestr)



        }
    }

    private fun init() {
        binding.swipe.setOnRefreshListener {
            binding.swipe.isRefreshing = true
            getRssNews()
        }
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        )
        adapter = MyAdapter(ArrayList<MyData>())
        adapter.itemClickListener = object : MyAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(adapter.items[position].url))
                startActivity(intent)
            }
        }
        binding.recyclerView.adapter = adapter
        getRssNews()
    }
}