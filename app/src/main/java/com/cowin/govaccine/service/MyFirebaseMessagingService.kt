package com.cowin.govaccine.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import com.cowin.govaccine.R
import com.cowin.govaccine.ui.main.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {

    private val TAG = "ServiceClass"

    override fun onMessageReceived( remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        val data = remoteMessage.notification
        pushNotification(data?.title,data?.body)
    }

    private fun pushNotification(title: String?, body: String?) {

        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val intent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                "channelId",
                "description",
                NotificationManager.IMPORTANCE_HIGH
            )

            notificationChannel.apply {
                lightColor = applicationContext.resources.getColor(R.color.colorPrimary)
                enableVibration(true)
            }


            notificationManager.createNotificationChannel(notificationChannel)

            val builder = Notification.Builder(applicationContext, "channelId")
            builder.apply {
                setContentTitle(title)
                setContentText(body)
                setSmallIcon(R.drawable.logo2)
                setLargeIcon(
                    BitmapFactory.decodeResource(
                        applicationContext.resources,
                        R.drawable.logo2
                    )
                )
                setContentIntent(pendingIntent)
            }

            notificationManager.notify(234, builder.build())

        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token");
    }


}