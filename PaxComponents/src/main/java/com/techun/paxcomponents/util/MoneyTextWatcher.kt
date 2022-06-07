package com.techun.paxcomponents.util

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.lang.ref.WeakReference
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.*


class MoneyTextWatcher(editText: EditText?) : TextWatcher {
    private val numberFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale.US)
    private var editTextWeakReference: WeakReference<EditText>? = null

    init {
        editTextWeakReference = WeakReference(editText)
        numberFormat.maximumFractionDigits = 0
        numberFormat.roundingMode = RoundingMode.FLOOR
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable?) {
        val editText = editTextWeakReference!!.get()
        if (editText == null || editText.text.toString() == "") {
            return
        }
        editText.removeTextChangedListener(this)

        val parsed: BigDecimal = parseCurrencyValue(editText.text.toString())
        val formatted: String = numberFormat.format(parsed)

        editText.setText(formatted)
        editText.setSelection(formatted.length)
        editText.addTextChangedListener(this)
    }

    private fun parseCurrencyValue(value: String): BigDecimal {
        try {
            val replaceRegex = String.format(
                "[%s,.\\s]",
                Objects.requireNonNull(numberFormat.currency).displayName
            )
            val currencyValue = value.replace(replaceRegex.toRegex(), "")
            return BigDecimal(currencyValue)
        } catch (e: Exception) {
            Util.logsUtils("Visaappk ${e.message}")
        }
        return BigDecimal.ZERO
    }
}