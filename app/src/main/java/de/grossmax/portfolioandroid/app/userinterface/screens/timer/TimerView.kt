package de.grossmax.portfolioandroid.app.userinterface.screens.timer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TimerView(viewModel: TimerViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val hours by viewModel.hours.observeAsState()
        val minutes by viewModel.minutes.observeAsState()
        val seconds by viewModel.seconds.observeAsState()

        Row {
            Text(hours.asStringWithFixedLength(2),
                fontSize = 50.sp,
                modifier = Modifier.clickable {
                    viewModel.increaseHours()
                }
            )
            Text(":", fontSize = 50.sp, modifier = Modifier.padding(10.dp, 0.dp))
            Text(
                minutes.asStringWithFixedLength(2),
                fontSize = 50.sp,
                modifier = Modifier.clickable {
                    viewModel.increaseMinutes()
                }
            )
            Text(":", fontSize = 50.sp, modifier = Modifier.padding(10.dp, 0.dp))
            Text(seconds.asStringWithFixedLength(2),
                fontSize = 50.sp,
                modifier = Modifier.clickable {
                    viewModel.increaseSeconds()
                }
            )
        }

        Row {
            Button(onClick = { viewModel.start() }) {
                Text("Start")
            }
            Button(onClick = { viewModel.stop() }) {
                Text("Stop")
            }
            Button(onClick = { viewModel.reset() }) {
                Text("Reset")
            }
        }
    }
}

fun Int?.asStringWithFixedLength(length: Int): String {
    return this?.toString()?.padStart(length, '0') ?: "0".repeat(length)
}