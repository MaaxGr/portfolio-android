package de.grossmax.portfolioandroid.app.userinterface.screens.takeawaytracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class TakeAwayTrackerRebootReceiver: BroadcastReceiver(), KoinComponent {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            get<TakeAwayTrackerModule>().scheduleReminder(context)
        }
    }

}