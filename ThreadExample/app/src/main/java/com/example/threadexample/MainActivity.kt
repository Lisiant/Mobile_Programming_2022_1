package com.example.threadexample

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.threadexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Looper: 간단한 메시지 처리를 위해 메시지 루프를 처리 -> 다른 쓰레드의 메시지를 꺼내서 처리하는 것
        // 메인 쓰레드에 Looper로 메시지 처리.
        val handler = Handler(Looper.getMainLooper())

        val imageList = arrayListOf<Int>()
        imageList.add(R.drawable.ic_launcher_background)
        imageList.add(R.drawable.ic_launcher_foreground)
        imageList.add(com.google.android.material.R.drawable.ic_mtrl_chip_checked_black)
        imageList.add(com.google.android.material.R.drawable.ic_clock_black_24dp)
        imageList.add(R.drawable.ic_launcher_background)
        imageList.add(R.drawable.ic_launcher_foreground)
        imageList.add(com.google.android.material.R.drawable.ic_mtrl_chip_checked_black)
        imageList.add(com.google.android.material.R.drawable.ic_clock_black_24dp)

        //오류: Main Thread만 View Rendering을 할 수 있음.
        Thread {
            for (image in imageList) {

                // 해결 방법 1. 메인 쓰레드에 전달하기
//                handler.post {
//                    binding.imageView.setImageResource(image)
//                }

                // 2. 상속받은 액티비티에서 함수 제공: runOnUithread
                runOnUiThread {
                    binding.imageView.setImageResource(image)
                }

                Thread.sleep(2000)
            }
        }.start()

        //해결: 핸들러 만들기.

//        val a = A()
//        val b = B()
//        a.start()
//        a.join() // critical section?
//        b.start()
    }

    class A : Thread() {
        override fun run() {
            super.run()
            for (i in 1..1000) {
                Log.d("test", "first: $i")
            }
        }
    }

    class B : Thread() {
        override fun run() {
            super.run()
            for (i in 1000 downTo 1) {
                Log.d("test", "second: $i")
            }
        }
    }
}
