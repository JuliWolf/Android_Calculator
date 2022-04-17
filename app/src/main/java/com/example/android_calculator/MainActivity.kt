package com.example.android_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var lastDot : Boolean = false
    var lastNumeric: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit (view: View) {
        tvInput.append((view as Button).text)
        lastNumeric = true
    }

    fun onClear (view: View) {
        tvInput.text = ""
        lastDot = false
        lastNumeric = false
    }

    fun onDecimalPoint (view: View) {
        if (!lastNumeric || lastDot) return

        tvInput.append(".")
        lastDot = true
        lastNumeric = false
    }

    fun onOperator (view: View) {
        if (!lastNumeric || isOperatorAdded(tvInput.text.toString())) return

        tvInput.append((view as Button).text)
        lastDot = false
        lastNumeric = false
    }

    fun onEqual (view: View) {
        if (!lastNumeric) return

        try {
            var tvValue = tvInput.text.toString().trim()
            var prefix = ""

            if (tvValue.startsWith("-")){
                prefix = "-"
                tvValue = tvValue.substring(1)
            }

            if (tvValue.contains("-")) {
                val splitValue = tvValue.split("-")

                val text = ("$prefix${splitValue[0]}".toDouble() - splitValue[1].toDouble()).toString()
                tvInput.text = removeZeroAfterDot(text)
            } else if (tvValue.contains("*")) {
                val splitValue = tvValue.split("*")

                val text = ("$prefix${splitValue[0]}".toDouble() * splitValue[1].toDouble()).toString()
                tvInput.text = removeZeroAfterDot(text)
            } else if (tvValue.contains("+")) {
                val splitValue = tvValue.split("+")

                val text = ("$prefix${splitValue[0]}".toDouble() + splitValue[1].toDouble()).toString()
                tvInput.text = removeZeroAfterDot(text)
            } else if (tvValue.contains("/")) {
                val splitValue = tvValue.split("/")

                val text = ("$prefix${splitValue[0]}".toDouble() / splitValue[1].toDouble()).toString()
                tvInput.text = removeZeroAfterDot(text)
            }
        } catch (e: java.lang.ArithmeticException) {
            e.printStackTrace()
        }
    }

    private fun isOperatorAdded (value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") ||
            value.contains("*") ||
            value.contains("+") ||
            value.contains("-")
        }
    }

    private fun removeZeroAfterDot (result: String): String {
        if (!result.contains(".0")) return result

        return result.substring(0, result.length - 2)
    }
}