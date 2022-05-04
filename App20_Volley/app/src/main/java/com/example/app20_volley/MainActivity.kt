package com.example.app20_volley

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.app20_volley.databinding.ActivityMainBinding
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var requestQueue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout() {
        requestQueue = Volley.newRequestQueue(this)
        binding.apply {
            button.setOnClickListener {
                progressBar.visibility = View.VISIBLE
                val stringRequest = StringRequest(
                    Request.Method.GET,
                    editText.text.toString(),
                    {
                        textView.text = String(it.toByteArray(Charsets.ISO_8859_1), Charsets.UTF_8)
                        //textView.text = it
                        progressBar.visibility = View.GONE
                    },
                    {
                        textView.text = it.message
                    }
                )
                requestQueue.add(stringRequest)
            }
        }

    }
}