package com.example.app25_sqlitepractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.app25_sqlitepractice.databinding.ActivityMainBinding
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var myDBHelper: MyDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initDB()
        init()
        getAllRecord()
    }

    private fun initDB() {
        val dbFile = getDatabasePath("mydb.db")
        if (!dbFile.parentFile.exists()){
            dbFile.parentFile.mkdir()
        }
        if (!dbFile.exists()) {
            val file = resources.openRawResource(R.raw.mydb)
            val fileSize = file.available()
            val buffer = ByteArray(fileSize)
            file.read(buffer)
            file.close()
            dbFile.createNewFile()
            val output = FileOutputStream(dbFile)
            output.write(buffer)
            output.close()
        }
    }

    private fun getAllRecord(){
        myDBHelper.getAllRecord()
    }

    private fun clearEditText(){
        binding.apply {
            pIdEdit.text.clear()
            pNameEdit.text.clear()
            pQuantityEdit.text.clear()
        }
    }

    private fun init() {
        myDBHelper = MyDBHelper(this)
        binding.apply {
            testsql.addTextChangedListener {
                val pName = it.toString()
                myDBHelper.findProduct2(pName)
            }
            insertbtn.setOnClickListener {
                val name = pNameEdit.text.toString()
                val quantity = pQuantityEdit.text.toString().toInt()
                val product = Product(0, name, quantity)
                val result = myDBHelper.insertProduct(product)
                if (result) {
                    getAllRecord()
                    Toast.makeText(this@MainActivity, "Data INSERT SUCCESS", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Data INSERT FAILED", Toast.LENGTH_SHORT).show()
                }
                clearEditText()

            }
            findbtn.setOnClickListener {
                val name = pNameEdit.text.toString()
                val result = myDBHelper.findProduct(name)
                if (result) {
                    Toast.makeText(this@MainActivity, "Record Found", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "NO MATCH", Toast.LENGTH_SHORT).show()
                }
            }
            deletebtn.setOnClickListener {
                val pid = pIdEdit.text.toString()
                val result = myDBHelper.deleteProduct(pid)
                if (result) {
                    Toast.makeText(this@MainActivity, "Data DELETE SUCCESS", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Data DELETE FAILED", Toast.LENGTH_SHORT).show()
                }
                getAllRecord()
                clearEditText()
            }
            updatebtn.setOnClickListener {
                val pid = pIdEdit.text.toString().toInt()
                val name = pNameEdit.text.toString()
                val quantity = pQuantityEdit.text.toString().toInt()
                val product = Product(pid, name, quantity)
                val result = myDBHelper.updateProduct(product)
                if (result) {
                    getAllRecord()
                    Toast.makeText(this@MainActivity, "Data UPDATE SUCCESS", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Data UPDATE FAILED", Toast.LENGTH_SHORT).show()
                }
                clearEditText()
            }

        }

    }
}