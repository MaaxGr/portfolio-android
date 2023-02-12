package de.grossmax.portfolioandroid.app.userinterface.screens.takeawaytracker.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.NotificationAdd
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import de.grossmax.portfolioandroid.app.businesslogic.screens.takeawaytracker.TakeAwayTrackerRepo
import de.grossmax.portfolioandroid.app.userinterface.layout.AppTopBarWithBackButton
import de.grossmax.portfolioandroid.app.userinterface.screens.takeawaytracker.TakeAwayTrackerUtils
import de.grossmax.portfolioandroid.app.userinterface.theme.DeepBlue
import org.koin.androidx.compose.get

@Composable
fun TakeAwayTrackerView(
    navHostController: NavHostController,
) {

    val context = LocalContext.current

    val viewModel = get<TakeAwayTrackerListViewModel>()
    val uiState by viewModel.uiState

    LaunchedEffect(key1 = true) {
        viewModel.init()
    }

    Column(
        modifier = Modifier
            .background(DeepBlue)
            .fillMaxSize()
    ) {

        AppTopBarWithBackButton(
            title = "Takeaways",
            navController = navHostController,
            rightContent = {
                Icon(
                    Icons.Default.Info,
                    contentDescription = "info",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(15.dp)
                        .size(24.dp)
                        .clickable {
                            navHostController.navigate("takeawaytracker/info")
                        }
                )

                Icon(
                    Icons.Default.NotificationAdd,
                    contentDescription = "notification",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(15.dp)
                        .size(24.dp)
                        .clickable {
                            TakeAwayTrackerUtils.sendNotification(context)
                        }
                )


                Icon(
                    Icons.Default.Add,
                    contentDescription = "plus",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(15.dp)
                        .size(24.dp)
                        .clickable {
                            navHostController.navigate("takeawaytracker/book")
                        }
                )
            }
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
                .padding(top = 15.dp)
        ) {
            ViewContent(uiState = uiState)
        }
    }

}

@Composable
private fun ViewContent(
    uiState: TakeAwayTrackerListViewModel.UIState
) {

    Column {
        uiState.takeAways.forEach { takeaway ->
            TakeAwayItem(takeaway = takeaway)
        }
    }
}

@Composable
private fun TakeAwayItem(
    takeaway: TakeAwayTrackerRepo.TakeAway
) {
    Text(
        text = takeaway.what + " " + takeaway.price,
        color = Color.White,
        fontSize = 20.sp,
        modifier = Modifier.padding(15.dp)
    )
}