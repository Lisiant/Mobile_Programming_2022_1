package com.example.app18_tablayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.app18_tablayout.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val textArr = arrayListOf<String>("이미지", "리스트")
    private val iconArr = arrayListOf<Int>(
        R.drawable.ic_baseline_aspect_ratio_24,
        R.drawable.ic_baseline_local_drink_24
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()

    }

    private fun initLayout() {
        binding.viewPager.adapter = MyViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = textArr[position]
            tab.setIcon(iconArr[position])
        }.attach()


    }
}