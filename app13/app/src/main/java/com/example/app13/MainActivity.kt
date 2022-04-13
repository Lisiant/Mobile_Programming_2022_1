package com.example.app13

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app13.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: MyAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()

    }


    private fun initRecyclerView() {
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter = MyAdapter(ArrayList<MyData>())

        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val applist = packageManager.queryIntentActivities(intent, 0)
        if (applist.size >0){
            for (appinfo in applist){
                val applabel = appinfo.loadLabel(packageManager).toString()
                val appclass = appinfo.activityInfo.name
                val appPackName = appinfo.activityInfo.packageName
                val appicon = appinfo.loadIcon(packageManager)
                adapter.items.add(MyData(applabel, appclass, appPackName, appicon))
            }
        }

        adapter.itemClickListener = object : MyAdapter.OnItemClickListener {
            override fun onItemClick(data: MyData) {
                //넘어온 앱리스트를 실행하는 기능 수행.

                val intent = packageManager.getLaunchIntentForPackage(data.apppackname)
                startActivity(intent)
            }
        }

        binding.recyclerView.adapter = adapter


    }
}