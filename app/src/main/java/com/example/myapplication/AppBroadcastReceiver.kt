package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

class AppBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val database = AppDatabase.getInstance(it)
            val action = Action(System.currentTimeMillis(), intent?.action.toString())
            database.addAction(action)
        }
    }
}