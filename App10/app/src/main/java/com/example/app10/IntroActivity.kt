package com.example.app10

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.app10.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {
    lateinit var binding: ActivityIntroBinding

    val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if (it.resultCode == Activity.RESULT_OK){
            val voc = it.data?.getSerializableExtra("voc") as MyData
            Toast.makeText(this,voc.word + "단어가 추가됨",Toast.LENGTH_SHORT).show()
        }
        Log.d("TESTTOAST", (it.resultCode == Activity.RESULT_OK).toString())

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout() {
        binding.button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.button2.setOnClickListener {
            val intent = Intent(this, AddVocActivity::class.java)
            activityResultLauncher.launch(intent)
            //startActivity(intent)
        }
    }
}