package com.example.clone10

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.clone10.databinding.ActivityAddVocBinding
import java.io.PrintStream

class AddVocActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddVocBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddVocBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout() {
        binding.apply {
            button3.setOnClickListener {
                val word = wordEt.text.toString()
                val meaning = meaningEt.text.toString()
                if (word == "" || meaning == "") {
                    Toast.makeText(applicationContext, "올바른 단어를 입력하세요", Toast.LENGTH_SHORT).show()
                } else {
                    writeFile(word, meaning)
                    Toast.makeText(applicationContext, "단어장에 $word 단어 추가 중...", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            button4.setOnClickListener {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }
        }

    }

    private fun writeFile(word: String, meaning: String) {
        val output = PrintStream(openFileOutput("out.txt", Context.MODE_APPEND))
        output.println(word)
        output.println(meaning)
        output.close()

        val intent = Intent()
        intent.putExtra("voc", MyData(word, meaning))
        setResult(Activity.RESULT_OK, intent)

    }
}