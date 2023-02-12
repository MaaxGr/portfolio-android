package de.grossmax.portfolioandroid.app.userinterface.screens.takeawaytracker.info

import android.app.AlarmManager
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import org.koin.core.component.KoinComponent


class TakeAwayTrackerInfoViewModel : ViewModel(), KoinComponent {

    val uiState = mutableStateOf(UIState.default())

    data class UIState(
        val alarmState: AlarmState
    ) {

        companion object {
            fun default() = UIState(
                alarmState = AlarmState.NotInitialized
            )
        }
    }

    sealed class AlarmState {
        object NotInitialized : AlarmState()
        object NoAlarm : AlarmState()
        data class Alarm(val time: Long) : AlarmState()
    }

    fun load(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val nextAlarm = alarmManager.nextAlarmClock

        if (nextAlarm == null) {
            uiState.value = uiState.value.copy(alarmState = AlarmState.NoAlarm)
        } else {
            uiState.value = uiState.value.copy(alarmState = AlarmState.Alarm(nextAlarm.triggerTime))
        }
    }


}