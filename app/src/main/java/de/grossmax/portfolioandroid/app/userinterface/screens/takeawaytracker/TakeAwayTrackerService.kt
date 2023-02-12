package de.grossmax.portfolioandroid.app.userinterface.screens.takeawaytracker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class TakeAwayTrackerService : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        TakeAwayTrackerUtils.sendNotification(context)
    }


}