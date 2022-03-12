package com.meow.toilet.log.util.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meow.toilet.log.R
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

// region recyclerView

@BindingAdapter("adapter")
fun <T : RecyclerView.ViewHolder> RecyclerView.setAdapter(adapter: RecyclerView.Adapter<T?>) {
    this.adapter = adapter
    this.layoutManager = LinearLayoutManager(context)
}

// endregion recyclerView

// region TextView

@BindingAdapter("text")
fun TextView.setText(value: LocalDate?) {
    this.text = try {
        value?.format(DateTimeFormatter.ofPattern("yyyy年M月d日(E)", Locale.JAPANESE)) ?:context.getString(R.string.data_empty)
    } catch (t: Throwable) {
        context.getString(R.string.data_empty)
    }
}

@BindingAdapter("text", "patternResId")
fun TextView.setText(value: LocalDate?, patternResId: Int) {
    this.text = try {
        value?.format(DateTimeFormatter.ofPattern(context.getString(patternResId), Locale.JAPANESE)) ?: context.getString(R.string.data_empty)
    } catch (t: Throwable) {
        context.getString(R.string.data_empty)
    }
}

//@BindingAdapter("text")
//fun TextView.setText(value: LocalDateTime) {
//    this.text = try {
//        value.format(DateTimeFormatter.ofPattern("yyyy年M月d日(E)", Locale.JAPANESE))
//    } catch (t: Throwable) {
//        context.getString(R.string.data_empty)
//    }
//}

// endregion TextView