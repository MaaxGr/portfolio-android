package de.grossmax.portfolioandroid.app.userinterface.screens.timer

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TimerViewModel : ViewModel(), KoinComponent {

    private val timerServiceModel: TimerServiceModel by inject()

    val serviceState: StateFlow<TimerServiceModel.State> = timerServiceModel.state
    val seconds: StateFlow<Int> = timerServiceModel.seconds

    fun start() {
        timerServiceModel.emit(TimerServiceModel.ViewEvent.Start)
    }

    fun stop() {
        timerServiceModel.emit(TimerServiceModel.ViewEvent.Stop)
    }

    fun reset() {
        timerServiceModel.emit(TimerServiceModel.ViewEvent.Reset)
    }

    fun increaseHours() {
        timerServiceModel.emit(
            TimerServiceModel.ViewEvent.IncreaseTimer(
                unit = TimerServiceModel.TimeUnit.Hours,
                value = 1
            )
        )
    }

    fun increaseMinutes() {
        timerServiceModel.emit(
            TimerServiceModel.ViewEvent.IncreaseTimer(
                unit = TimerServiceModel.TimeUnit.Minutes,
                value = 1
            )
        )
    }

    fun increaseSeconds() {
        timerServiceModel.emit(
            TimerServiceModel.ViewEvent.IncreaseTimer(
                unit = TimerServiceModel.TimeUnit.Seconds,
                value = 1
            )
        )
    }

}