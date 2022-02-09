package com.nprmanbrandons11.alarmmanager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.widget.Toast

import android.content.Intent

import android.content.BroadcastReceiver
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        Log.i("ALARM","Alarm worked")
        Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show()
    }


}