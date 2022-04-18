package com.example.app10

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app10.databinding.ActivityMainBinding
import java.lang.Exception
import java.util.*

class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding
    //lateinit var recyclerView: RecyclerView
    val data: ArrayList<MyData> = ArrayList()
    lateinit var adapter: MyAdapter
    lateinit var tts: TextToSpeech
    var ttsReady = false




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        initRecyclerView()
        initTTS()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (ttsReady) {
            tts.stop()
            tts.shutdown()
        }
    }

    private fun initTTS() {
        tts = TextToSpeech(this, TextToSpeech.OnInitListener {
            ttsReady = true
            tts.language = Locale.US
        })


    }

    private fun initRecyclerView() {
        //recyclerView = findViewById(R.id.recyclerView)
        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = MyAdapter(data)
        adapter.itemClickListener = object : MyAdapter.OnItemClickListener {
            override fun onItemClick(data: MyData, meaningTextView: TextView) {
                Toast.makeText(applicationContext, data.meaning, Toast.LENGTH_SHORT).show()
                if (ttsReady) {
                    tts.speak(data.word, TextToSpeech.QUEUE_ADD, null, null)
                }
                data.isOpened = !data.isOpened
                if (!data.isOpened) {
                    meaningTextView.visibility = View.GONE
                } else {
                    meaningTextView.visibility = View.VISIBLE
                }
            }
        }

        binding.recyclerView.adapter = adapter

        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                adapter.moveItem(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.removeItem(viewHolder.adapterPosition)
            }

        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

    }


    private fun readFileScan(scan: Scanner){
        while (scan.hasNextLine()) {
            val word = scan.nextLine()
            val meaning = scan.nextLine()
            data.add(MyData(word, meaning))
        }

    }

    private fun initData() {

        try{
            val scan2 = Scanner(openFileInput("out.txt"))
            readFileScan(scan2)

        }catch (e: Exception){
            Toast.makeText(this, "추가된 단어 없음", Toast.LENGTH_SHORT).show()
        }

        val scan = Scanner(resources.openRawResource(R.raw.words))
        readFileScan(scan)
    }
}