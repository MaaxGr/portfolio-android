package de.grossmax.portfolioandroid.app.userinterface.screens.takeawaytracker.list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.grossmax.portfolioandroid.app.businesslogic.screens.takeawaytracker.TakeAwayTrackerRepo
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TakeAwayTrackerListViewModel: ViewModel(), KoinComponent {

    // dependencies
    private val repo: TakeAwayTrackerRepo by inject()

    // variables
    val uiState = mutableStateOf(UIState.default())

    fun init() {
        viewModelScope.launch {
            repo.getTakeAways().collect {
                uiState.value = uiState.value.copy(takeAways = it)
            }
        }
    }

    data class UIState(
        val takeAways: List<TakeAwayTrackerRepo.TakeAway>
    ) {
        companion object {
            fun default() = UIState(
                takeAways = emptyList()
            )
        }
    }


}