package com.example.test2021

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.test2021.databinding.JwparkActivityIntroBinding

class JWParkIntroActivity : AppCompatActivity() {

    lateinit var binding: JwparkActivityIntroBinding
    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){

        if (it.resultCode == Activity.RESULT_OK){
            val productInfo = it.data?.getSerializableExtra("productInfo") as JWParkMyData
            Toast.makeText(this, "${productInfo.productName}, ${productInfo.productPrice}", Toast.LENGTH_SHORT).show()
        }
    }



    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it){
                callAction()
            }else{
                Toast.makeText(this, "권한 취소됨", Toast.LENGTH_SHORT).show()
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = JwparkActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout() {
        binding.phoneNumber.setOnClickListener {
            callAction()
        }

        binding.button1.setOnClickListener {
            val intent = Intent(this, JWParkListActivity::class.java)
            startActivity(intent)
        }

        binding.button2.setOnClickListener {
            val intent = Intent(this, JWParkResultActivity::class.java)
            activityResultLauncher.launch(intent)
        }
    }

    private fun callAction() {
        val phoneNumber = binding.phoneNumber.text.toString()
        val number = Uri.parse("tel:$phoneNumber")
        val callIntent = Intent(Intent.ACTION_CALL, number)
        when {
            ((ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE))
                    == PackageManager.PERMISSION_GRANTED) -> {
                startActivity(callIntent)
            }

            ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CALL_PHONE) -> {
                callAlertDlg()
            }
            else -> {
                requestPermissionLauncher.launch(android.Manifest.permission.CALL_PHONE)
            }
        }

    }

    private fun callAlertDlg() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("반드시 CALL_PHONE 권한이 허용되어야 합니다.")
            .setTitle("권한허용")
            .setPositiveButton("OK") { _, _ ->
                requestPermissionLauncher.launch(android.Manifest.permission.CALL_PHONE)
            }
            .setNegativeButton("Cancel") { dlg, _ ->
                dlg.dismiss()
            }
        val dlg = builder.create()
        dlg.show()
    }
}