package com.example.mobileprogramming1

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun init() {
        val bmiButton = findViewById<Button>(R.id.bmibutton)
        val weight = findViewById<EditText>(R.id.weight)
        val height = findViewById<EditText>(R.id.height)
        val bmiFace = findViewById<ImageView>(R.id.bmiFace)

        bmiButton.setOnClickListener {
            val bmi =
                weight.text.toString().toDouble() / (height.text.toString().toDouble() / 100).pow(
                    2.0
                )
            var resultString: String
            when {
                bmi >= 35 -> {
                    resultString = "고도 비만"
                    bmiFace.setImageResource(R.drawable.ic_baseline_sentiment_very_dissatisfied_24)
                }

                bmi >= 23 -> {
                    resultString = "과체중"
                    bmiFace.setImageResource(R.drawable.ic_baseline_sentiment_dissatisfied_24)
                }

                bmi >= 18.5 -> {
                    resultString = "정상"
                    bmiFace.setImageResource(R.drawable.ic_baseline_sentiment_satisfied_alt_24)
                }
                else -> {
                    resultString = "저체중"
                    bmiFace.setImageResource(R.drawable.ic_baseline_sentiment_satisfied_24)
                }
            }
            Toast.makeText(this, resultString, Toast.LENGTH_SHORT).show()

        }

    }


}

