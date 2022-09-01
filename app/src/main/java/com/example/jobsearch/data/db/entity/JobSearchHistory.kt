package com.example.jobsearch.data.db.entity

import android.annotation.SuppressLint
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("ConstantLocale")
private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

@Entity(
    tableName = "job_search_history",
)
data class JobSearchHistory(
    @PrimaryKey
    @ColumnInfo(name = "job_type")
    val jobType: String,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long
) {

    operator fun component3(): String {
        return simpleDateFormat.format(Date(timestamp))
    }

    override fun toString(): String {
        return """${this.javaClass.simpleName}(jobType=${jobType}, stamp=${timestamp}, time=${component3()})"""
    }

}