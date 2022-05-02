package com.example.app17_fragment_communication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.app17_fragment_communication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val myViewModel: MyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout() {
        val fragment = supportFragmentManager.beginTransaction()
        val imgFragment = ImageFragment()
        fragment.replace(R.id.frameLayout, imgFragment)
        fragment.commit()

    }
}