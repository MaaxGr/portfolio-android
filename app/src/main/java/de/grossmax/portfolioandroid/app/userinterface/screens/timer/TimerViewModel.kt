package de.grossmax.portfolioandroid.app.userinterface.screens.timer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class TimerViewModel: ViewModel() {

    val hours = MutableLiveData(0)
    val minutes = MutableLiveData(0)
    val seconds = MutableLiveData(0)


    var started = false

    fun start() {
        started = true
        viewModelScope.launch {
            while (started) {
                if (seconds.value == 0 && minutes.value == 0 && seconds.value == 0) {
                    started = false
                } else {
                    if (seconds.value == 0) {
                        if (minutes.value == 0) {
                            hours.value = hours.value?.minus(1)
                            minutes.value = 59
                            seconds.value = 59
                        } else {
                            minutes.value = minutes.value?.minus(1)
                            seconds.value = 59
                        }
                    } else {
                        seconds.value = seconds.value?.minus(1)
                    }
                }

                delay(1000)
            }
        }
    }

    fun stop() {
        started = false
    }

    fun reset() {
        hours.postValue(0)
        minutes.postValue(0)
        seconds.postValue(0)
    }

    fun increaseHours() {
        hours.postValue(hours.value!! + 1)
    }

    fun increaseMinutes() {
        minutes.postValue(minutes.value!! + 1)
    }

    fun increaseSeconds() {
        seconds.postValue(seconds.value!! + 1)
    }

}