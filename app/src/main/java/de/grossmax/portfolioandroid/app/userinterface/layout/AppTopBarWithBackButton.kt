package de.grossmax.portfolioandroid.app.userinterface.layout

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

fun navigateBackTo(navController: NavController, destinationRoute: String?) {

    if (destinationRoute == null) {
        navController.popBackStack()
        return
    }


    val hasBackstackTheDestinationRoute = navController.backQueue.find {
        it.destination.route == destinationRoute
    } != null

    // if the destination is already in the backstack, simply go back
    if (hasBackstackTheDestinationRoute) {
        navController.popBackStack(destinationRoute, false)
    } else {
        // otherwise, navigate to a new destination popping the current destination
        navController.navigate(destinationRoute) {
            navController.currentBackStackEntry?.destination?.route?.let {
                popUpTo(it) {
                    inclusive = true
                }
            }
        }
    }
}


@Composable
fun AppTopBarWithBackButton(
    title: String,
    navController: NavController,
    customBack: String? = null,
    rightContent: @Composable () -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            //.padding(15.dp),
    ) {

        Row {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "back",
                tint = Color.White,
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
                    .clickable {
                        navigateBackTo(navController, customBack)
                    }
            )

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(15.dp)
            ) {
                Text(
                    title,
                    style = MaterialTheme.typography.h2
                )
            }
        }

        Row {
            rightContent()
        }
    }
}
