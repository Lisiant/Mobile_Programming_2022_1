package com.example.app05

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.core.widget.addTextChangedListener

class MainActivity : AppCompatActivity() {


    private val countries = mutableListOf(
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
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }

        val items = resources.getStringArray(R.array.countries_array)
        val adapter2 = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line,
            items
        )

        multiAutoCompleteTextView.setAdapter(adapter2)
        multiAutoCompleteTextView.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())

        val editText = findViewById<EditText>(R.id.editText)
        val button = findViewById<Button>(R.id.button)



//       1. TextWatcher의 익명 개체로 만들어서 넣는 것 : 인터페이스의 모든 메서드 오버라이딩
//        editText.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//                val str = p0.toString()
//                button.isEnabled = str.isNotEmpty()
//            }
//        })


//      2. 모든 함수의 body를 구현 - 필요한 것만 오버라이딩 하는 것
//        editText.addTextChangedListener(afterTextChanged = {
//            val str = it.toString()
//            button.isEnabled = str.isNotEmpty()
//        })


//      3. afterTextChanged를 많이 사용하므로 그냥 미리 구현해놓은 것.
        editText.addTextChangedListener {
            val str = it.toString()
            button.isEnabled = str.isNotEmpty()
        }

            button.setOnClickListener {
            adapter.add(editText.text.toString())
            adapter.notifyDataSetChanged()
            editText.text.clear()

        }


    }
}

