package com.example.week3.helper


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import android.widget.RemoteViews
import com.example.week3.R

class CustomNotificationHelper(private val context: Context) {

    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    // Notification Channel ID for Android 8.0 and above
    private val CHANNEL_ID = "custom_notification_channel"
    private val CHANNEL_NAME = "Custom Notifications"
    private val CHANNEL_DESCRIPTION = "Show custom notifications"

    init {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = CHANNEL_DESCRIPTION
            notificationManager.createNotificationChannel(channel)
        }
    }

    // Function to build and show the notification with custom layout
    fun showCustomNotification(title: String, message: String, iconResId: Int) {
        val customLayout = RemoteViews(context.packageName, R.layout.custom_notification_layout)
        customLayout.setTextViewText(R.id.notification_title, title)
        customLayout.setTextViewText(R.id.notification_text, message)
        customLayout.setImageViewResource(R.id.notification_icon, iconResId)

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.vidgram_logo_top)  // Default small icon
            .setCustomContentView(customLayout)  // Use custom layout
            .setPriority(NotificationCompat.PRIORITY_HIGH)  // High priority
            .setAutoCancel(true)  // Auto-dismiss on click
            .build()

        // Show the notification
        notificationManager.notify(101, notification)
    }

    // Function to add actions to the notification (Optional)
    fun showCustomNotificationWithAction(
        title: String,
        message: String,
        iconResId: Int,
        actionLabel: String,
        pendingIntent: PendingIntent
    ) {
        val customLayout = RemoteViews(context.packageName, R.layout.custom_notification_layout)
        customLayout.setTextViewText(R.id.notification_title, title)
        customLayout.setTextViewText(R.id.notification_text, message)
        customLayout.setImageViewResource(R.id.notification_icon, iconResId)

        val action = NotificationCompat.Action.Builder(
            R.drawable.vidgram_logo_top, actionLabel, pendingIntent
        ).build()

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.vidgram_logo_top)
            .setCustomContentView(customLayout)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .addAction(action)  // Add action to the notification
            .build()

        notificationManager.notify(101, notification)
    }
}
