package com.example.jobsearch.util

import android.util.Log

@Suppress("UNCHECKED_CAST")
fun <T : Any> Any?.safeAs(): T? = this as? T

fun log(tag: String, message: String) {
    Log.d(tag, "[${Thread.currentThread().name}] $message")
}