package com.example.app04

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.MultiAutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    val countries = arrayOf(
        "Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra",
        "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina",
        "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan",
        "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initLayout()
    }

    private fun initLayout() {

        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line,
            countries
        )
        val autoCompleteTextView = findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        val multiAutoCompleteTextView =
            findViewById<MultiAutoCompleteTextView>(R.id.multiAutoCompleteTextView)

        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.setOnItemClickListener { adapterView, view, i, l ->
            val msg = adapterView.getItemAtPosition(i).toString()
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
        }

        val items = resources.getStringArray(R.array.countries_array)
        val adapter2 = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line,
            items
        )

        multiAutoCompleteTextView.setAdapter(adapter2)
        multiAutoCompleteTextView.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())




    }
}