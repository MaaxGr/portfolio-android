package de.grossmax.portfolioandroid.app.userinterface.screens.timer

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import de.grossmax.portfolioandroid.app.userinterface.layout.AppButton
import de.grossmax.portfolioandroid.app.userinterface.layout.AppTopBarWithBackButton
import de.grossmax.portfolioandroid.app.userinterface.theme.BlueViolet3
import de.grossmax.portfolioandroid.app.userinterface.theme.DeepBlue
import de.grossmax.portfolioandroid.app.userinterface.theme.TextWhite

@Composable
fun TimerView(
    navController: NavHostController
) {

    Column(
        modifier = Modifier
            .background(DeepBlue)
            .fillMaxSize()
    ) {

        AppTopBarWithBackButton(
            title = "Timer",
            navController = navController
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
                .padding(top = 15.dp)
        ) {
            TimerViewContent(navController = navController)
        }
    }
}

@Composable
fun TimerViewContent(
    viewModel: TimerViewModel = viewModel(),
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .padding(start = 15.dp, bottom = 15.dp, end = 15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = BlueViolet3)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val state by viewModel.serviceState.collectAsState()
        val seconds by viewModel.seconds.collectAsState()




        Row(
            modifier = Modifier
        ) {
            Text(
                seconds.asFullHours().asStringWithFixedLength(2),
                fontSize = 50.sp,
                color = TextWhite,
                modifier = Modifier.clickable {
                    viewModel.increaseHours()
                }
            )
            Text(":", color = TextWhite, fontSize = 50.sp, modifier = Modifier.padding(10.dp, 0.dp))
            Text(
                seconds.asRemainingMinutes().asStringWithFixedLength(2),
                fontSize = 50.sp,
                color = TextWhite,
                modifier = Modifier.clickable {
                    viewModel.increaseMinutes()
                }
            )
            Text(":", color = TextWhite, fontSize = 50.sp, modifier = Modifier.padding(10.dp, 0.dp))
            Text(
                seconds.asRemainingSeconds().asStringWithFixedLength(2),
                fontSize = 50.sp,
                color = TextWhite,
                modifier = Modifier.clickable {
                    viewModel.increaseSeconds()
                }
            )
        }

        Row(
            modifier = Modifier.padding(bottom = 15.dp)
        ) {
            val context = LocalContext.current
            val coroutineScope = rememberCoroutineScope()

            AppButton(
                text = "Start",
                onClick = {
                    viewModel.start()

                    val intent = Intent(context, TimerService::class.java)
                    ContextCompat.startForegroundService(context, intent)
                }
            )
            AppButton(
                text = "Stop",
                modifier = Modifier
                    .padding(start = 15.dp),
                onClick = {
                    viewModel.stop()
                }
            )
            AppButton(
                text = "Reset",
                modifier = Modifier
                    .padding(start = 15.dp),
                onClick = {
                    viewModel.reset()
                }
            )
        }
    }
}