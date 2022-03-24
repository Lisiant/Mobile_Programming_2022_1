package com.example.app06

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initLayout()
    }

    fun initLayout() {
        val textInputLayout = findViewById<TextInputLayout>(R.id.textInputLayout1)
        val emailText = findViewById<TextInputEditText>(R.id.email_text)
        emailText.addTextChangedListener {
            if (it.toString().contains('@')) {
                textInputLayout.error = null
            } else {
                textInputLayout.error = "이메일 형식이 올바르지 않습니다."
            }
        }
    }

}