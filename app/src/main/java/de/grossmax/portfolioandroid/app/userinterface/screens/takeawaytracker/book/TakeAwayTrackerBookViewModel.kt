package de.grossmax.portfolioandroid.app.userinterface.screens.takeawaytracker.book

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.grossmax.portfolioandroid.app.businesslogic.screens.takeawaytracker.TakeAwayTrackerRepo
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TakeAwayTrackerBookViewModel : ViewModel(), KoinComponent {

    // dependencies
    private val repo: TakeAwayTrackerRepo by inject()

    // variables
    val uiState = mutableStateOf(UIState.default())

    fun setInputContentWhat(inputContentWhat: String) {
        uiState.value = uiState.value.copy(inputContentWhat = inputContentWhat)
    }

    fun setInputContentPrice(inputContentPrice: String) {
        uiState.value = uiState.value.copy(inputContentPrice = inputContentPrice)
    }

    fun submit() {
        viewModelScope.launch {
            repo.addTakeAway(
                TakeAwayTrackerRepo.TakeAwayDraft(
                    what = uiState.value.inputContentWhat,
                    price = uiState.value.inputContentPrice,
                )
            )
            uiState.value = uiState.value.copy(submitted = true)
        }
    }


    data class UIState(
        val inputContentWhat: String,
        val inputContentPrice: String,
        val submitted: Boolean
    ) {

        companion object {
            fun default() = UIState(
                inputContentWhat = "",
                inputContentPrice = "",
                submitted = false
            )
        }

        fun isPriceValid(): Boolean {
            return inputContentPrice.matches(Regex("^[0-9]+(\\.[0-9]{1,2})?\$"))
        }

        fun isShowInvalidPriceError(): Boolean {
            return !isPriceValid() && inputContentPrice.isNotEmpty()
        }

        fun isWhatValid(): Boolean {
            return inputContentWhat.isNotBlank()
        }

        fun isFormValid(): Boolean {
            return isWhatValid() && isPriceValid()
        }



    }



}