package com.example.app10

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.app10.databinding.ActivityAddVocBinding
import java.io.PrintStream
import java.util.*

class AddVocActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddVocBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddVocBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()

    }

    private fun initLayout() {
        binding.apply {
            addBtn.setOnClickListener{
                val word = wordEt.text.toString()
                val meaning = meaningEt.text.toString()
                writeFile(word, meaning)

            }

            cancelBtn.setOnClickListener{
                // 취소했다는 것을 알림 => Intro에서 resultCode가 RESULT_OK가 아니므로 넘어감.
                setResult(Activity.RESULT_CANCELED)
                finish()
            }

        }
    }

    private fun writeFile(word: String, meaning: String){
        val output = PrintStream(openFileOutput("out.txt", Context.MODE_APPEND))
        output.println(word)
        output.println(meaning)
        output.close()
        val intent = Intent()
        intent.putExtra("voc", MyData(word, meaning))
        setResult(Activity.RESULT_OK, intent)
    }

}