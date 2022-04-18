package com.example.clone12

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.clone12.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val CALL_REQUEST = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
    }


    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                Toast.makeText(this, "권한 승인됨", Toast.LENGTH_SHORT).show()
                callAction()

            } else {
                Toast.makeText(this, "권한 거부됨", Toast.LENGTH_SHORT).show()
            }

        }

    private fun callAction() {
        val number = Uri.parse("tel:010-8569-2810")
        val callIntent = Intent(Intent.ACTION_CALL, number)

        when {
            ((ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CALL_PHONE
            )) == PackageManager.PERMISSION_GRANTED)
            -> {
                startActivity(callIntent)
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.CALL_PHONE
            ) -> {
                callAlertDlg()
            }
            else -> {
                requestPermissionLauncher.launch(android.Manifest.permission.CALL_PHONE)
            }
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
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

    private fun callAlertDlg() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("권한.")
            .setTitle("권한 체크")
            .setPositiveButton("OKAY") { _, _ ->
                requestPermissionLauncher.launch(android.Manifest.permission.CALL_PHONE)
            }
            .setNegativeButton("Cancel") { dlg, _ ->
                dlg.dismiss()
            }

        val dlg = builder.create()
        dlg.show()
    }


    private fun initLayout() {
        with(binding) {

            button1.setOnClickListener {
                callAction()
            }

            button2.setOnClickListener {
                val msg = Uri.parse("sms:010-1234-1234")
                val msgIntent = Intent(Intent.ACTION_SENDTO, msg)
                msgIntent.putExtra("sms_body", "빨리 다음꺼 하자 ....")
                startActivity(msgIntent)
            }

            button3.setOnClickListener {
                val webpage = Uri.parse("https://www.naver.com")
                val webIntent = Intent(Intent.ACTION_VIEW, webpage)
                startActivity(webIntent)
            }

            button4.setOnClickListener {
                val location = Uri.parse("geo:37.543684, 127.077130?z=16")
                val mapIntent = Intent(Intent.ACTION_VIEW, location)
                startActivity(mapIntent)
            }

        }


    }

}