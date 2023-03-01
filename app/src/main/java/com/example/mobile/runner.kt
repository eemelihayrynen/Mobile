package com.example.mobile

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.RemoteViews
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.mobile.ui.theme.MobileTheme
import java.util.*

// NotificationRequestWorker extends the worker class which defines the work to be performed by the worker.
class NotificationRequestWorker(val context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    override fun doWork(): Result {
        // Do the work here
        // declaring variables
        lateinit var notificationManager: NotificationManager
        lateinit var notificationChannel: NotificationChannel
        lateinit var builder: Notification.Builder

        val channelId = "i.apps.notifications"
        val description = "Test notification"

        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // RemoteViews are used to use the content of
        // some different layout apart from the current activity layout


            // checking if android version is greater than oreo(API 26) or not
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel =
                NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(context, channelId)
                .setSubText("Reminder: " + Calendar.getInstance().get(Calendar.HOUR).toString() + ":" + Calendar.getInstance().get(Calendar.MINUTE).toString())
                .setSmallIcon(R.drawable.ic_launcher_background)

        } else {

            builder = Notification.Builder(context)
                .setSubText("Reminder: " + Calendar.getInstance().get(Calendar.HOUR).toString() + ":" + Calendar.getInstance().get(Calendar.MINUTE).toString())
                .setSmallIcon(R.drawable.ic_launcher_background)


        }
        notificationManager.notify(1234, builder.build())

    return Result.success()
}
    }
