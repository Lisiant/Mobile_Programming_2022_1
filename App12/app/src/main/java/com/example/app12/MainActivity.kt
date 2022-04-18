package com.example.app12

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.app12.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val CALL_REQUEST = 100


    private val permissions = arrayOf(
        android.Manifest.permission.CALL_PHONE,
        android.Manifest.permission.CAMERA
    )

    private val requestMultiplePermissionsLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            val resultPermission = it.all {
                it.value == true
            }
            if (resultPermission) {
                Toast.makeText(this, "모든 권한 승인됨", Toast.LENGTH_SHORT).show()
                callAction()
            } else {
                Toast.makeText(this, "권한 거부됨", Toast.LENGTH_SHORT).show()
            }
        }

    val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                Toast.makeText(this, "모든 권한 승인됨", Toast.LENGTH_SHORT).show()
                callAction()
            } else {
                Toast.makeText(this, "권한 거부됨", Toast.LENGTH_SHORT).show()

            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
    }


    private fun callAction() {
        val number = Uri.parse("tel:010-1234-1234")
        val callIntent = Intent(Intent.ACTION_CALL, number)
        when {
            ((ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE)) == PackageManager.PERMISSION_GRANTED) &&
                 ((ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)) == PackageManager.PERMISSION_GRANTED) -> {
                    startActivity(callIntent)
            }

            ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CALL_PHONE) -> {
                callAlertDlg()
            }
            else -> {
                requestMultiplePermissionsLauncher.launch(permissions)
//                requestPermissionLauncher.launch(android.Manifest.permission.CALL_PHONE)
//                ActivityCompat.requestPermissions(
//                    this, arrayOf(android.Manifest.permission.CALL_PHONE), CALL_REQUEST
//                )
            }
        }

    }

    private fun callAlertDlg() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("반드시 CALL_PHONE, CAMERA 권한 필요")
            .setTitle("권한 체크")
            .setPositiveButton("OKAY") { _, _ ->
                requestMultiplePermissionsLauncher.launch(permissions)
                // requestPermissionLauncher.launch(android.Manifest.permission.CALL_PHONE)
                // ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE), CALL_REQUEST)

            }
            .setNegativeButton("Cancel") { dlg, _ ->
                dlg.dismiss()
            }
        val dlg = builder.create()
        dlg.show()

    }

    // 사용자의 퍼미션 허용이 끝나면 자동으로 호출되는 함수
    // 사용자의 퍼미션 허용 결과를 확인하는 함수이다
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        // 퍼미션 정보
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CALL_REQUEST -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "권한 승인됨", Toast.LENGTH_SHORT).show()
                    callAction()
                } else {
                    Toast.makeText(this, "권한 거부됨", Toast.LENGTH_SHORT).show()
                }
            }
        }

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
                callAction()
            }

        }


    }

}