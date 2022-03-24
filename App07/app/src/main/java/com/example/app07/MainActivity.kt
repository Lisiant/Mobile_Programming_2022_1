package com.example.app07

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    var mediaPlayer: MediaPlayer? = null
    var vol = 0.5f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initLayout()
    }

    private fun initLayout() {
        val imageView = findViewById<VolumeControlView>(R.id.imageView)
        imageView.setVolumeListener(object : VolumeControlView.VolumeListener {

            // mediaPlayer에서는 0~1 사이의 값으로 볼륨 조절.
            // angle 값은 -180 ~ 180 -> 변환 필요
            override fun onChanged(angle: Float) {
                vol = if (angle > 0) {
                    angle / 360
                } else {
                    (360 + angle) / 360
                }
                mediaPlayer?.setVolume(vol, vol)
            }

        })

        val playBtn = findViewById<Button>(R.id.playBtn)
        playBtn.setOnClickListener {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, R.raw.music_lilac)
                mediaPlayer?.setVolume(vol, vol)
            }
            mediaPlayer?.start()
        }

        val stopBtn = findViewById<Button>(R.id.stopBtn)
        stopBtn.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }

        val pauseBtn = findViewById<Button>(R.id.pauseBtn)
        pauseBtn.setOnClickListener {
            mediaPlayer?.pause()

        }


    }


}