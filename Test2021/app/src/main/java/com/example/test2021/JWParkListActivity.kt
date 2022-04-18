package com.example.test2021

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test2021.databinding.JwparkActivityListBinding
import java.io.PrintStream
import java.util.*

class JWParkListActivity : AppCompatActivity() {

    private lateinit var binding: JwparkActivityListBinding
    private val data = ArrayList<JWParkMyData>()
    private lateinit var adapter:JWParkAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = JwparkActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.listRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = JWParkAdapter(data)
        adapter.itemClickListener = object: JWParkAdapter.OnItemClickListener{
            override fun onItemClick(data: JWParkMyData, position: Int) {

                val name = data.productName
                val price = data.productPrice
                writeFile(name, price)

                val intent1 = Intent(applicationContext, JWParkIntroActivity::class.java)
                startActivity(intent1)
            }

        }

        binding.listRv.adapter = adapter
    }

    private fun writeFile(name: String, price: Int) {
        val output = PrintStream(openFileOutput("out.txt", MODE_APPEND))
        output.println(name)
        output.println(price)
        output.close()

        val intent = Intent()
        intent.putExtra("productInfo", JWParkMyData(name, price))
        setResult(Activity.RESULT_OK, intent)

    }


    private fun initData() {

        val scan = Scanner(resources.openRawResource(R.raw.jwpark))
        while (scan.hasNextLine()) {
            val productName = scan.nextLine()
            val productPrice = scan.nextLine().toInt()
            data.add(JWParkMyData(productName, productPrice))
        }
    }

}