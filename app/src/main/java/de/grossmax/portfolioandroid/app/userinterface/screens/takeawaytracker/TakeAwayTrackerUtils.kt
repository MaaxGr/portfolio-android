package de.grossmax.portfolioandroid.app.userinterface.screens.takeawaytracker

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import de.grossmax.portfolioandroid.R
import de.grossmax.portfolioandroid.app.userinterface.MainActivity
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

object TakeAwayTrackerUtils: KoinComponent {

    const val CHANNEL_ID = "takeaway_channel"
    const val CHANNEL_DESCRIPTION = "Notifications for TakeAwayTracker"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                /* id = */ CHANNEL_ID,
                /* name = */ CHANNEL_DESCRIPTION,
                /* importance = */ NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = CHANNEL_DESCRIPTION
            }
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun sendNotification(context: Context) {
        // tapResultIntent gets executed when user taps the notification
        val tapResultIntent = Intent(context, MainActivity::class.java)
        tapResultIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP


        val intent = Intent(
            Intent.ACTION_VIEW,
            "https://portfolio.maax.gr/takeawaytracker/book".toUri(),
            context,
            MainActivity::class.java
        )

        val pendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        context.getString(R.string.screen_takeawaytracker_notification_title)

        val notification = context.let {
            NotificationCompat.Builder(it, CHANNEL_ID)
                .setContentTitle(context.getString(R.string.screen_takeawaytracker_notification_title))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .build()
        }

        val notificationManager = context.let { NotificationManagerCompat.from(it) }
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }

        notificationManager.notify(1, notification)

        // reschedule reminder
        get<TakeAwayTrackerModule>().scheduleReminder(context)
    }

}