package com.example.app28_broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.app28_broadcastreceiver.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        initLayout()
        initPermission()
        checkSettingOverlayWindow(intent)
        //getMessage(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        checkSettingOverlayWindow(intent)
    }

    fun getMessage(intent: Intent?) {
        if (intent != null) {
            if (intent.hasExtra("msgSender") and intent.hasExtra("msgBody")) {
                val smsSender = intent.getStringExtra("msgSender")
                val smsBody = intent.getStringExtra("msgBody")
                Toast.makeText(this, "보낸 번호:$smsSender\n$smsBody", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun checkSettingOverlayWindow(intent: Intent?){
       if(Settings.canDrawOverlays(this)){
           getMessage(intent)
       }else{
           val overlayIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
           requestSettingLauncher.launch(overlayIntent)
       }
    }

    val requestSettingLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (Settings.canDrawOverlays(this)) {
                getMessage(this.intent)
            } else {
                Toast.makeText(this, "권한 승인이 거부되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                initPermission()
            } else {
                Toast.makeText(this, "권한 승인이 거부되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    private fun permissionAlertDlg() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("반드시 문자 수신 권한이 허용되어야 합니다.")
            .setTitle("권한 체크")
            .setPositiveButton("OK") { _, _ ->
                requestPermissionLauncher.launch(android.Manifest.permission.RECEIVE_SMS)
            }
            .setNegativeButton("cancel") { dlg, _ ->
                dlg.dismiss()
            }
        val dlg = builder.create()
        dlg.show()

    }

    private fun initPermission() {
        when {
            (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED) -> {
                Toast.makeText(this, "문자 수신 동의함", Toast.LENGTH_SHORT).show()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this, android.Manifest.permission.RECEIVE_SMS) -> {
                permissionAlertDlg()
            }

            else -> {
                requestPermissionLauncher.launch(android.Manifest.permission.RECEIVE_SMS)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter(Intent.ACTION_POWER_CONNECTED)
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED)
        registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(broadcastReceiver)
    }

    private fun initLayout() {
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                if (p1 != null) {
                    if (p1.action.equals(Intent.ACTION_POWER_CONNECTED)) {
                        Toast.makeText(p0, "충전 시작", Toast.LENGTH_SHORT).show()
                    } else if (p1.action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
                        Toast.makeText(p0, "충전 해제", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}