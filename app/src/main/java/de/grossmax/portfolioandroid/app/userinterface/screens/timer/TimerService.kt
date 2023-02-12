package de.grossmax.portfolioandroid.app.userinterface.screens.timer

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import de.grossmax.portfolioandroid.app.userinterface.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class TimerService: Service(), KoinComponent {

    private val timerServiceModel: TimerServiceModel by inject()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private var channelId: String = ""
    private val notificationId = 1

    private var notificationVisible = false

    override fun onCreate() {
        super.onCreate()

        Log.i("TimerService", "onCreate visible: $notificationVisible")

        if (!notificationVisible && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId = createNotificationChannel("my_service", "My Background Service")
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("TimerService", "onStartCommand ${intent?.action ?: "null"}, $flags")

        if (!notificationVisible) {
            notificationVisible = true

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notification = getNotification("Timer")

                startForeground(notificationId, notification)

                coroutineScope.launch {
                    timerServiceModel.events.collectLatest {
                        if (it is TimerServiceModel.ViewEvent.Stop) {
                            if (notificationVisible) {
                                notificationVisible = false
                                stopSelf()
                            }
                        }
                    }
                }

                coroutineScope.launch {
                    timerServiceModel.seconds.collectLatest {
                        updateNotification(it)
                    }
                }

            }

        }

        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String{
        val chan = NotificationChannel(channelId,
            channelName, NotificationManager.IMPORTANCE_NONE)
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }

    private fun getNotification(text: String): Notification {
        val pendingIntent: PendingIntent =
            Intent(this, MainActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(this, 0, notificationIntent,
                    PendingIntent.FLAG_IMMUTABLE)
            }

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Timer")
            .setContentText(text)
            .setSmallIcon(android.R.drawable.sym_def_app_icon)
            .setContentIntent(pendingIntent)
            .setTicker("Ticket jo")
            .build()
    }

    private fun updateNotification(remainingSeconds: Int) {
        val hours = remainingSeconds.asFullHours()
        val minutes = remainingSeconds.asRemainingMinutes()
        val seconds = remainingSeconds.asRemainingSeconds()

        var text = ""

        if (hours> 0) {
            text = "The Timer rings in $hours hours and $minutes minutes"
        } else {
            text = "The Timer rings in $minutes minutes and $seconds seconds"
        }
        val notification: Notification = getNotification(text)
        val mNotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(notificationId, notification)
    }

}