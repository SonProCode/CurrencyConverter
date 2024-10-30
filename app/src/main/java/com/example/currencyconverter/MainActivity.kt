package com.example.currencyconverter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var etSourceAmount: EditText
    private lateinit var etTargetAmount: EditText
    private lateinit var spinnerSourceCurrency: Spinner
    private lateinit var spinnerTargetCurrency: Spinner


    private val exchangeRates = mapOf(
        "VND" to 1.0,
        "AED" to 0.00014493,
        "AFN" to 0.002638,
        "ALL" to 0.003582,
        "AMD" to 0.01515,
        "ANG" to 0.00007064,
        "AOA" to 0.03593,
        "ARS" to 0.03904,
        "AUD" to 0.00006015,
        "BZD" to 0.00007893,
        "CAD" to 0.00005490,
        "CHF" to 0.00003424,
        "EUR" to 0.00003649,
        "GBP" to 0.00003035,
        "USD" to 0.00003947,

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        etSourceAmount = findViewById(R.id.etSourceAmount)
        etTargetAmount = findViewById(R.id.etTargetAmount)
        spinnerSourceCurrency = findViewById(R.id.spinnerSourceCurrency)
        spinnerTargetCurrency= findViewById(R.id.spinnerTargetCurrency)


        val dropDownList = exchangeRates.keys.toTypedArray()
        val adapter = ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, dropDownList)
        spinnerSourceCurrency.adapter = adapter
        spinnerTargetCurrency.adapter = adapter


        etSourceAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = convertCurrency()
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })


        spinnerSourceCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        spinnerTargetCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }


        etSourceAmount.setOnClickListener {
            spinnerSourceCurrency.requestFocus()
            spinnerTargetCurrency.clearFocus()
        }

        etTargetAmount.setOnClickListener {
            spinnerTargetCurrency.requestFocus()
            spinnerSourceCurrency.clearFocus()
        }
    }

    private fun convertCurrency() {

        val amount = etSourceAmount.text.toString().toDoubleOrNull() ?: return
        val fromCurrency = spinnerSourceCurrency.selectedItem.toString()
        val toCurrency = spinnerTargetCurrency.selectedItem.toString()


        val fromRate = exchangeRates[fromCurrency] ?: return
        val toRate = exchangeRates[toCurrency] ?: return


        val result = amount * (toRate / fromRate)
        etTargetAmount.setText(String.format("%.2f", result))
    }
}