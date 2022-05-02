package com.example.app16_dynamicfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.app16_dynamicfragment.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val imgFragment = ImageFragment()
    val itemFragment = ItemFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout() {
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.frameLayout, imgFragment)
        fragment.commit()

        binding.apply {
            button1.setOnClickListener {
                if (!imgFragment.isVisible) {
                    val fragment = supportFragmentManager.beginTransaction()
                    fragment.replace(R.id.frameLayout, imgFragment)
                    fragment.addToBackStack(null)
                    fragment.commit()
                }

            }
            button2.setOnClickListener {
                if (!itemFragment.isVisible) {
                    val fragment = supportFragmentManager.beginTransaction()
                    fragment.replace(R.id.frameLayout, itemFragment)
                    fragment.addToBackStack(null)
                    fragment.commit()
                }
            }

        }
    }
}