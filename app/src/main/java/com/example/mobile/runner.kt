package com.example.mobile

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

// NotificationRequestWorker extends the worker class which defines the work to be performed by the worker.
class NotificationRequestWorker(val context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    override fun doWork(): Result {
        // Do the work here
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(context, "first").apply {
            setContentTitle("Background task")
            setContentText("Sample text")
            setSmallIcon(R.drawable.ic_launcher_foreground)
            priority = NotificationCompat.PRIORITY_DEFAULT
        }.build()

        nm.notify(System.currentTimeMillis().toInt(), notification)

        // Indicate whether the task finished successfully with the Result
        return Result.success()
    }}
