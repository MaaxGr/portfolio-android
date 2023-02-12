package de.grossmax.portfolioandroid.app.userinterface.screens.timer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

class TimerServiceModel {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State.Stopped)
    val state: StateFlow<State> = _state.asStateFlow()

    private val _seconds: MutableStateFlow<Int> = MutableStateFlow(0)
    val seconds: StateFlow<Int> = _seconds.asStateFlow()

    private val _events: MutableSharedFlow<ViewEvent> = MutableSharedFlow()
    val events: SharedFlow<ViewEvent> = _events

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    var timer = Timer()

    fun emit(event: ViewEvent) {
        coroutineScope.launch {
            _events.emit(event)

            when (event) {
                is ViewEvent.Start -> onStart()
                is ViewEvent.Stop -> onStop()
                is ViewEvent.IncreaseTimer -> onIncreaseTimer(event)
                is ViewEvent.Reset -> onReset()
            }
        }
    }

    private fun onStart() {
        // do not start twice if already running
        if (_state.value == State.Running) {
            return
        }

        // set state to running
        _state.value = State.Running

        // start timer
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (_state.value != State.Running) {
                    stopTimer()
                    return
                }

                val remainingSeconds = _seconds.value
                if (remainingSeconds == 0) {
                    _state.value = State.Stopped
                    stopTimer()
                } else {
                    _seconds.value = _seconds.value - 1
                }
            }
        }, 0, 1000)
    }

    private fun stopTimer() {
        timer.cancel()
        timer = Timer()
    }

    private fun onStop() {
        _state.value = State.Stopped
    }

    private fun onIncreaseTimer(event: ViewEvent.IncreaseTimer) {
        _seconds.value = when (event.unit) {
            TimeUnit.Seconds -> _seconds.value + event.value
            TimeUnit.Minutes -> _seconds.value + event.value * 60
            TimeUnit.Hours -> _seconds.value + event.value * 60 * 60
        }
    }

    private fun onReset() {
        _seconds.value = 0
    }

    sealed class State() {
        object Stopped: State()
        object Paused: State()
        object Running: State()
    }

    enum class TimeUnit {
        Hours, Minutes, Seconds
    }

    sealed class ViewEvent {
        object Start : ViewEvent()
        object Stop : ViewEvent()
        object Reset : ViewEvent()
        data class IncreaseTimer(val unit: TimeUnit, val value: Int): ViewEvent()
    }

}