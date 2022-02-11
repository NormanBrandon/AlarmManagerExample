package com.nprmanbrandons11.alarmmanager

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.widget.Toast

import android.content.Intent

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import android.content.Context.NOTIFICATION_SERVICE
import android.graphics.Color
import androidx.core.content.ContextCompat

import androidx.core.content.ContextCompat.getSystemService
import android.app.PendingIntent
import android.R











class AlarmReceiver : BroadcastReceiver() {
    private lateinit var mNotificationManager: NotificationManager
    private val NOTIFICATION_ID = 0
    private val PRIMARY_CHANNEL_ID = "primary_notification_channel"

    override fun onReceive(context: Context?, intent: Intent?) {

        createNotificationChannel(context)

        //Log.i("ALARM","Alarm worked")
        //Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show()
    }

    fun createNotificationChannel(context: Context?) {

        // Create a notification manager object.
        mNotificationManager = context!!.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.O
        ) {

            // Create the NotificationChannel with all the parameters.
            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                "Tienes 10 likes mas",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Notifies every 15 minutes to stand up and walk"
            mNotificationManager.createNotificationChannel(notificationChannel)
        }
       deliverNotification(context)
    }
    private fun deliverNotification(context: Context) {
        val contentIntent = Intent(context, MainActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.arrow_up_float)
                .setContentTitle("Tienes 10 likes adicionales")
                .setContentText("Ahora gastatelos como te de en gana")
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
        mNotificationManager.notify(NOTIFICATION_ID, builder.build())
    }
}