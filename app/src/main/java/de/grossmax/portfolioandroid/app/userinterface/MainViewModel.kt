package de.grossmax.portfolioandroid.app.userinterface

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel: ViewModel() {

    val forceNavigationState = MutableStateFlow(false)

}