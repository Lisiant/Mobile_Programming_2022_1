package com.example.app10_another

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView:RecyclerView
    val data:ArrayList<MyData> = ArrayList()
    lateinit var adapter: MyAdapter
    lateinit var tts:TextToSpeech
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
        if(ttsReady){
            tts.stop()
            tts.shutdown()
        }
    }

    private fun initTTS() {
        tts = TextToSpeech(this , TextToSpeech.OnInitListener {
            ttsReady = true
            tts.language = Locale.US
        })
    }

    private fun changeVisible(view: View,isClicked : Boolean){
        if(isClicked){
            view.visibility = View.VISIBLE
        }
        else{
            view.visibility = View.GONE
        }
    }

    private fun initRecyclerView(){
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        adapter = MyAdapter(data)
        adapter.itemClickListner = object:MyAdapter.OnItemClickListner{
            override fun OnItemClick(data: MyData , textView: TextView) {
                if(ttsReady)
                    tts.speak(data.word, TextToSpeech.QUEUE_ADD, null)
                Toast.makeText(applicationContext , data.meaning, Toast.LENGTH_SHORT).show()
                data.isClicked = !data.isClicked
                changeVisible(textView,data.isClicked)
            }

        }
        recyclerView.adapter = adapter
        var simpleItemTouchCallback = object:ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT){
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
    }//initRecyclerview

    private fun initData(){
        val scan = Scanner(resources.openRawResource(R.raw.words))
        while(scan.hasNextLine()){
            val word = scan.nextLine()
            val meaning = scan.nextLine()
            data.add(MyData(word,meaning,false))
        }
    }
}
