package com.example.app02_imgselect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.RadioGroup
import com.example.app02_imgselect.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        init()
    }

    private fun init(){
        
        //val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        //val imageView = findViewById<ImageView>(R.id.imageView)

        binding.radioGroup.setOnCheckedChangeListener{radioGroup,checkedID->
            when(checkedID){
                 R.id.radioButton1 -> binding.imageView.setImageResource(R.drawable.img1)
                 R.id.radioButton2 -> binding.imageView.setImageResource(R.drawable.img2)
                 R.id.radioButton3 -> binding.imageView.setImageResource(R.drawable.img3)

            }
        }
    }
}