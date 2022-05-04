package com.example.app19_webview

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.app19_webview.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val scope = CoroutineScope(Dispatchers.Main)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        binding.apply {
            button.setOnClickListener {
                scope.launch {
//                    Log.i("CheckScope", Thread.currentThread().name)
                    progressBar.visibility = View.VISIBLE
                    var data = ""
                    withContext(Dispatchers.IO) {
                        data = loadNetwork(URL(editText.text.toString()))
                    }
//
//                    CoroutineScope(Dispatchers.IO).async {
//                        Log.i("CheckScope", Thread.currentThread().name)
//                        data = loadNetwork(URL(editText.text.toString()))
//                    }.await()
//                    Log.i("CheckScope", Thread.currentThread().name)
                    textView.text = data
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun loadNetwork(url: URL): String {
        var result = ""
        val connect = url.openConnection() as HttpURLConnection
        connect.connectTimeout = 4000
        connect.readTimeout = 4000
        connect.requestMethod = "GET"
        connect.connect()
        val responseCode = connect.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            result = streamToString(connect.inputStream)
        }
        return result
    }

    private fun streamToString(inputStream: InputStream): String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        var line: String
        var result = ""

        try {
            do {
                line = bufferedReader.readLine()
                if (line != null) result += line
                else break
            } while (true)
            inputStream.close()
        } catch (ex: Exception) {
            Log.e("error", "읽기 실패")
        }
        return result
    }

//    private fun init(){
//        binding.apply {
//            webView.webViewClient = WebViewClient()
//            webView.settings.javaScriptEnabled = true
//            webView.settings.builtInZoomControls = true
//            webView.settings.defaultTextEncodingName = "utf-8"
//            webView.loadUrl("https://www.google.com")
//        }
//    }
}