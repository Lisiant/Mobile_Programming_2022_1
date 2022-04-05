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
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    val data: ArrayList<MyData> = ArrayList()
    lateinit var adapter: MyAdapter
    lateinit var tts: TextToSpeech
    var ttsReady = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter = MyAdapter(data)


        adapter.itemClickListener = object : MyAdapter.OnItemClickListener {
            override fun OnItemClick(data: MyData, meaningTextView: TextView) {
                //this하면 익명 클래스이므로 안됨, applicationContext를 통해 어디서든 context 정보를 얻을 수 있음.
                Toast.makeText(applicationContext, data.meaning, Toast.LENGTH_SHORT).show()
                if (ttsReady) {
                    tts.speak(data.word, TextToSpeech.QUEUE_ADD, null, null)
                }

                if (data.isOpened) {
                    meaningTextView.visibility = View.GONE
                    data.isOpened = false
                } else {
                    meaningTextView.visibility = View.VISIBLE
                    data.isOpened = true

                }
            }
        }

        recyclerView.adapter = adapter

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
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }


    private fun initData() {
        val scan = Scanner(resources.openRawResource(R.raw.words))
        while (scan.hasNextLine()) {
            val word = scan.nextLine()
            val meaning = scan.nextLine()
            data.add(MyData(word, meaning, false))
        }

    }
}