package de.grossmax.portfolioandroid.app.userinterface.screens.takeawaytracker

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*

class TakeAwayTrackerModule {


    fun scheduleReminder(appContext: Context) {
        val alarmManager = appContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(appContext, TakeAwayTrackerService::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            /* context = */ appContext,
            /* requestCode = */ 1,
            /* intent = */ intent,
            /* flags = */ PendingIntent.FLAG_UPDATE_CURRENT
        )


        val minute = Calendar.getInstance(TimeZone.getDefault()).get(Calendar.MINUTE) + 5

        val calendar = Calendar.getInstance(TimeZone.getDefault()).apply {
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }

    //    if (calendarPlusOneMinute.timeInMillis - calendar.timeInMillis > 0
     //   ) {
      //      calendar.add(Calendar.DATE, 1)
      //  }

        alarmManager.setAlarmClock(
            AlarmManager.AlarmClockInfo(calendar.timeInMillis, pendingIntent),
            pendingIntent
        )

    }

}