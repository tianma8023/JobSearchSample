package com.example.jobsearch.util

import android.view.View
import android.widget.TextView

fun TextView.bindText(text: CharSequence?, bindVisibility: Boolean = true) {
    this.text = text
    if (bindVisibility) {
        this.visibility = if (text.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }
}