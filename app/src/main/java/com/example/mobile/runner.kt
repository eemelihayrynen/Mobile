package com.example.mobile

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.*

// NotificationRequestWorker extends the worker class which defines the work to be performed by the worker.
class NotificationRequestWorker(val context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    @SuppressLint("UnspecifiedImmutableFlag")
    override fun doWork(): Result {
        // Do the work here
        // declaring variables
        lateinit var notificationManager: NotificationManager
        lateinit var notificationChannel: NotificationChannel
        lateinit var builder: Notification.Builder

        val channelId = "i.apps.notifications"
        val description = "Test notification"
        val hour = Calendar.getInstance().get(Calendar.HOUR).toString()
        val min = Calendar.getInstance().get(Calendar.MINUTE).toString()
        val texters = inputData.getString("message")
        val locationx = inputData.getString("locationx")
        val locationy = inputData.getString("locationy")
        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        var intent = Intent(context, MainActivity6::class.java)
        Log.wtf("message: ", "hoihoihoi")
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            // checking if android version is greater than oreo(API 26) or not
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel =
                NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)
            builder = Notification.Builder(context, channelId)
                .setSubText("Reminder: $hour:$min")
                .setContentText(texters)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
        } else {
            builder = Notification.Builder(context)
                .setSubText("Reminder: $hour:$min")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentText(texters)
                .setContentIntent(pendingIntent)

        }
        notificationManager.notify(1234, builder.build())

        fun pm(hour:String):String{
            if (Calendar.getInstance().get(Calendar.AM_PM)==1){
                val hours = hour.toInt()+12
                return hours.toString()
            }
            return hour
        }
        pm(hour)
        context.startActivity(Intent(context, MainActivity5::class.java).putExtra("hour",hour).putExtra("message",texters.toString()).putExtra("min",min).putExtra("locationx", locationx).putExtra("locationy",locationy).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

    return Result.success()
}
    }
