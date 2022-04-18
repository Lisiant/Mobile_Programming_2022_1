package com.example.recyclerviewpractice1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recyclerviewpractice1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}