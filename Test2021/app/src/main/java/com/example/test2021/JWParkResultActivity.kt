package com.example.test2021

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test2021.databinding.JwparkActivityResultBinding
import java.util.*
import kotlin.collections.ArrayList

class JWParkResultActivity : AppCompatActivity() {

    lateinit var binding: JwparkActivityResultBinding
    lateinit var adapter: JWParkResultAdapter
    val data = ArrayList<JWParkMyData>()
    private var sum = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = JwparkActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initRecyclerView()
    }

    private fun initData() {
        try{
            val scan2 = Scanner(openFileInput("out.txt"))
            readFileScan(scan2)
        }catch (e: Exception){
            Toast.makeText(this, "추가된 메뉴가 없습니다.", Toast.LENGTH_SHORT).show()
        }

        for (item in data){
            sum += item.productPrice
        }
        binding.totalPrice.text = sum.toString()


    }

    private fun readFileScan(scan: Scanner) {
        while (scan.hasNextLine()) {
            val productName = scan.nextLine()
            val productPrice = scan.nextLine().toInt()
            data.add(JWParkMyData(productName, productPrice))
        }
    }



    private fun initRecyclerView() {
        binding.resultRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = JWParkResultAdapter(data)

        binding.resultRv.adapter = adapter
        adapter.notifyDataSetChanged()

    }

}