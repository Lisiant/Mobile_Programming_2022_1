package com.example.app12

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.app12.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
    }

    private fun initLayout() {
        with(binding) {
            msgBtn.setOnClickListener {
                val msg = Uri.parse("sms:010-1234-1234")
                val msgIntent = Intent(Intent.ACTION_SENDTO, msg)
                msgIntent.putExtra("sms_body", "빨리 다음꺼 하자 ....")
                startActivity(msgIntent)
            }

            webBtn.setOnClickListener {
                val webpage = Uri.parse("https://www.naver.com")
                val webIntent = Intent(Intent.ACTION_VIEW, webpage)
                startActivity(webIntent)
            }

            mapBtn.setOnClickListener {
                val location = Uri.parse("geo:37.543684, 127.077130?z=16")
                val mapIntent = Intent(Intent.ACTION_VIEW, location)
                startActivity(mapIntent)
            }

            callBtn.setOnClickListener {
                val number = Uri.parse("tel:010-1234-1234")
                val callIntent = Intent(Intent.ACTION_CALL, number)
                startActivity(callIntent)
            }

        }


    }
}