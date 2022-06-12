package com.example.myapplication

import java.text.SimpleDateFormat
import java.util.*

data class Action(
    val time: Long,
    val state: String
) {

    fun getDate(): String {
        val date = Date(time)
        val format = SimpleDateFormat("HH:mm:ss dd.MM.yyyy")
        return format.format(date)
    }
}