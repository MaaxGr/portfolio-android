package de.grossmax.portfolioandroid.app.userinterface.screens.takeawaytracker.info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import de.grossmax.portfolioandroid.app.userinterface.layout.AppTopBarWithBackButton
import de.grossmax.portfolioandroid.app.userinterface.theme.DeepBlue

@Composable
fun TakeAwayTrackerInfoView(
    navController: NavHostController,
    viewModel: TakeAwayTrackerInfoViewModel = viewModel(),
    ) {

    val context = LocalContext.current
    val uiState by viewModel.uiState

    LaunchedEffect(key1 = Unit) {
        viewModel.load(context)
    }

    Column(
        modifier = Modifier
            .background(DeepBlue)
            .fillMaxSize()
    ) {

        AppTopBarWithBackButton(
            title = "Takeaway System Info",
            navController = navController
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
                .padding(top = 15.dp)
        ) {
            CurrentViewContent(
                uiState = uiState,
            )
        }
    }
}

@Composable
private fun CurrentViewContent(
    uiState: TakeAwayTrackerInfoViewModel.UIState,
) {

    when (uiState.alarmState) {
        is TakeAwayTrackerInfoViewModel.AlarmState.NotInitialized -> {
            Text("Not initialized", color = Color.Red)
        }
        is TakeAwayTrackerInfoViewModel.AlarmState.NoAlarm -> {
            Text("No alarm!", color = Color.Red)
        }
        is TakeAwayTrackerInfoViewModel.AlarmState.Alarm -> {
            Text("Next alarm at ${uiState.alarmState.time}", color = Color.White)
        }
    }


}