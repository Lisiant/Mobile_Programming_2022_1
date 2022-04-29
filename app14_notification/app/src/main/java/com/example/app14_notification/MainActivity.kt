package com.example.app14_notification

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.example.app14_notification.databinding.ActivityMainBinding
import com.example.app14_notification.databinding.MypickerdlgBinding
import java.util.*

class MainActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {

    private lateinit var binding: ActivityMainBinding
    private var message = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // initLayout()
        initLayout2()
    }

    private fun initLayout2() {
        binding.calendarView.setOnDateChangeListener { calendarView, i, i2, i3 ->
            val dlgBinding = MypickerdlgBinding.inflate(layoutInflater)
            val dlgBuilder = AlertDialog.Builder(this)
            dlgBuilder.setView(dlgBinding.root)
                .setPositiveButton("추가") { _, _ ->
                    message = "${dlgBinding.timePicker.hour}시 ${dlgBinding.timePicker.minute}분 ${dlgBinding.msg.text}"
                    val timerTask = object : TimerTask() {
                        override fun run() {
                            makeNotification()
                        }
                    }
                    val timer = Timer()
                    timer.schedule(timerTask, 2000)
                    Toast.makeText(this, "알림이 추가됨", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("취소") { _, _ ->
                }
                .show()

        }
    }

    private fun initLayout() {
        binding.calendarView.setOnDateChangeListener { calendarView, i, i2, i3 ->
            val c = Calendar.getInstance()
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)
            val timePicker = TimePickerDialog(this, this, hour, minute, true)
            timePicker.show()
        }
    }

    //시간 정보 설정했을 경우
    override fun onTimeSet(p0: TimePicker?, p1: Int, p2: Int) {
        if (p0 != null) {
            message = "${p1}시 ${p2}분"
            val timerTask = object : TimerTask() {
                override fun run() {
                    makeNotification()
                }
            }
            val timer = Timer()
            timer.schedule(timerTask, 2000)
            Toast.makeText(this, "알림이 추가됨", Toast.LENGTH_SHORT).show()
        }
    }

    private fun makeNotification() {
        val id = "MyChannel"
        val name = "TimeCheckChannel"
        val notificationChannel =
            NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT)
        notificationChannel.enableVibration(true)
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.BLUE
        notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

        val builder = NotificationCompat.Builder(this, id)
            .setSmallIcon(R.drawable.ic_baseline_access_alarm_24)
            .setContentTitle("일정 알람")
            .setContentText(message)
            .setAutoCancel(true)

        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("time", message)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent =
            PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)

        val notification = builder.build()

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(notificationChannel)
        manager.notify(10, notification)


    }
}