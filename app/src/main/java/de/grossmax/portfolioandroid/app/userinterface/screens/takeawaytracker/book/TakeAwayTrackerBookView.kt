package de.grossmax.portfolioandroid.app.userinterface.screens.takeawaytracker.book

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import de.grossmax.portfolioandroid.R
import de.grossmax.portfolioandroid.app.userinterface.layout.AppTopBarWithBackButton
import de.grossmax.portfolioandroid.app.userinterface.layout.navigateBackTo
import de.grossmax.portfolioandroid.app.userinterface.theme.BlueViolet3
import de.grossmax.portfolioandroid.app.userinterface.theme.ButtonBlue
import de.grossmax.portfolioandroid.app.userinterface.theme.DeepBlue
import de.grossmax.portfolioandroid.app.userinterface.theme.TextWhite

@Composable
fun TakeAwayTrackerBookView(
    navController: NavHostController,
    customBack: String? = null,
    viewModel: TakeAwayTrackerBookViewModel = viewModel()
) {
    BackHandler { navigateBackTo(navController, customBack) }

    val uiState by viewModel.uiState

    // navigate back after success submit
    LaunchedEffect(key1 = uiState.submitted) {
        if (uiState.submitted) {
            navigateBackTo(navController, customBack)
        }
    }

    Column(
        modifier = Modifier
            .background(DeepBlue)
            .fillMaxSize()
    ) {

        AppTopBarWithBackButton(
            title = "Book Takeaway",
            navController = navController,
            customBack = customBack
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(1f)
                .padding(top = 15.dp)
        ) {
            CurrentViewContent(
                uiState = uiState,
                onWhatUpdate = { viewModel.setInputContentWhat(it) },
                onPriceUpdate = { viewModel.setInputContentPrice(it) },
                onSubmit = {
                    viewModel.submit()
                }
            )
        }
    }

}

@Composable
private fun CurrentViewContent(
    uiState: TakeAwayTrackerBookViewModel.UIState,
    onWhatUpdate: (String) -> Unit,
    onPriceUpdate: (String) -> Unit,
    onSubmit: () -> Unit
) {

    Box(
        modifier = Modifier
            .padding(start = 15.dp, bottom = 15.dp, end = 15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(color = BlueViolet3)
            .fillMaxWidth(),
    ) {

        Column(modifier = Modifier.padding(15.dp)) {

            val lightBlue = Color(0xffd8e6ff)

            TextField(
                value = uiState.inputContentWhat,
                onValueChange = { onWhatUpdate(it) },
                label = { Text(stringResource(id = R.string.screen_takeawaytracker_book_input_what_label)) },
                isError = !uiState.isWhatValid(),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = lightBlue,
                    cursorColor = Color.Black,
                    disabledLabelColor = lightBlue,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                modifier = Modifier
                    .padding(bottom = 24.dp)
                    .fillMaxWidth()
            )

            TextField(
                value = uiState.inputContentPrice,
                onValueChange = { onPriceUpdate(it) },
                isError = !uiState.isPriceValid(),
                label = { Text(stringResource(id = R.string.screen_takeawaytracker_book_input_price_label)) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = lightBlue,
                    cursorColor = Color.Black,
                    disabledLabelColor = lightBlue,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
            )
            if (uiState.isShowInvalidPriceError()) {
                Text(
                    text = stringResource(id = R.string.screen_takeawaytracker_book_input_price_error),
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            Spacer(modifier = Modifier.padding(top = 24.dp))

            Button(
                onClick = onSubmit,
                enabled = uiState.isFormValid(),
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = ButtonBlue,
                    disabledBackgroundColor = Color.DarkGray
                )
            ) {
                Text(
                    text = stringResource(id = R.string.screen_takeawaytracker_book_button_book),
                    color = TextWhite,
                )
            }
        }
    }

}