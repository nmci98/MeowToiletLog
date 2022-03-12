package com.meow.toilet.log.base

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.meow.toilet.log.R
import timber.log.Timber
import java.time.LocalDate
import java.time.Month
import java.util.*

/**
 *  デイトピッカー.
 */
class DatePickerFragment(
    private val initialValue: LocalDate?
) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    /**
     * 日付選択リスナー.
     */
    interface OnDateSelectedListener {
        fun onDateSelected(year: Int, month: Month, dayOfMonth: Int)
    }

    /** リスナー */
    private var listener: OnDateSelectedListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = initialValue?.year ?: calendar.get(Calendar.YEAR)
        val month = initialValue?.monthValue?.minus(1) ?: calendar.get(Calendar.MONTH)
        val day = initialValue?.dayOfMonth ?: calendar.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(
            requireContext(),
            R.style.DatePickerDialogSpinner,
            this,
            year,
            month,
            day
        ).also {
            // 未来日を入力できないようにする
            it.datePicker.maxDate = calendar.timeInMillis
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = when {
                context is OnDateSelectedListener -> context
                parentFragment is OnDateSelectedListener -> parentFragment as OnDateSelectedListener
                else -> null
            }
        } catch (e: ClassCastException) {
            Timber.e(e)
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        // 月のは0〜11で取得されるので、Monthに変換するために+1する
        this.listener?.onDateSelected(year, Month.of(month + 1), dayOfMonth)
    }

}