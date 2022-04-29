package com.example.jwparkmidtest

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jwparkmidtest.databinding.ActivityMainBinding
import com.example.jwparkmidtest.databinding.RowBinding
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var layoutPosition = 0

    private var data = ArrayList<MyData>()
    lateinit var adapter: MyAdapter

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                callAction(layoutPosition)

            } else {
                data[layoutPosition].count+=1
                callAction(layoutPosition)
            }

        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        initRecyclerView()
        initLayout()
    }
    private fun initRecyclerView() {
        binding.mainRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = MyAdapter(data)

        adapter.itemClickListener = object : MyAdapter.OnItemClickListener {
            override fun onItemClick(data: MyData, position:Int) {
                callAction(position)
                adapter.notifyItemChanged(position)
                layoutPosition = position
            }
        }
        binding.mainRv.adapter = adapter
    }

    private fun callAction(position:Int) {
        val phoneNumber = data[position].phoneNumber
        val number = Uri.parse("tel:$phoneNumber")
        val callIntent = Intent(Intent.ACTION_CALL, number)

        when {
            ((ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CALL_PHONE
            )) == PackageManager.PERMISSION_GRANTED)
            -> {
                data[position].count += 1
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

    private fun callAlertDlg() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("반드시 CALL_PHONE 권한이 허용되어야 합니다.")
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

    private fun initData() {

        val scan = Scanner(resources.openRawResource(R.raw.maindata))
        readFileScan(scan)

    }

    private fun readFileScan(scan: Scanner) {
        while (scan.hasNextLine()) {
            val name = scan.nextLine()
            val companyName = scan.nextLine()
            val phoneNumber=  scan.nextLine()
            data.add(MyData(name, companyName, phoneNumber))
        }
    }

    private fun initLayout() {

    }


}