package com.app.calllogs

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MyNotificationReceiver : BroadcastReceiver() {

    companion object {
        const val CHANNEL_ID = "my_app_notification_channel"
        const val NOTIFICATION_ID = 1 // A unique ID for your notification
    }

    override fun onReceive(context: Context, intent: Intent) {
        // Create the notification channel if Android version is O or higher
        createNotificationChannel(context)

        val title = intent.getStringExtra("title")
        val id = intent.getStringExtra("id")
        val message = intent.getStringExtra("message")
        val number = intent.getStringExtra("number")

        val intent = Intent(context, MainActivity::class.java)
            .apply {
                // Add flags to ensure proper behavior when the app is launched from the notification
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        intent.putExtra("title", title)
        intent.putExtra("id", id)
        intent.putExtra("item", "1")
        intent.putExtra("number", number)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
// Optionally, add extras to the intent to pass data to the target activity


        // Build the notification
        val notificationLayout = RemoteViews(context.packageName, R.layout.notification_small)
        val notificationLayoutL = RemoteViews(context.packageName, R.layout.notification_small)

        notificationLayout.setTextViewText(R.id.notification_title, "Associate Call to Record")
        notificationLayout.setTextViewText(R.id.notification_name, title)
        notificationLayout.setTextViewText(R.id.notification_description, message)

        notificationLayoutL.setTextViewText(R.id.notification_title, "Associate Call to Record")
        notificationLayoutL.setTextViewText(R.id.notification_name, title)
        notificationLayoutL.setTextViewText(R.id.notification_description, message)


        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Replace with your small icon
            .setContentTitle(title)
            .setContentText(message)
            .setCustomContentView(notificationLayout)
            .setCustomBigContentView(notificationLayoutL)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true) // Dismisses the notification when tapped

        // Display the notification
        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "My Channel Name" // User-visible name
            val descriptionText = "Channel description" // User-visible description
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
                // Ensure heads-up behavior is allowed
                enableLights(true)
                enableVibration(true)
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
