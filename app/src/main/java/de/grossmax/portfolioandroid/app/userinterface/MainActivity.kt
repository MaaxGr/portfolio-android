package de.grossmax.portfolioandroid.app.userinterface

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import de.grossmax.portfolioandroid.app.userinterface.screens._home.HomeView
import de.grossmax.portfolioandroid.app.userinterface.screens.mcdcoupons.MCDCouponsView
import de.grossmax.portfolioandroid.app.userinterface.screens.takeawaytracker.book.TakeAwayTrackerBookView
import de.grossmax.portfolioandroid.app.userinterface.screens.takeawaytracker.info.TakeAwayTrackerInfoView
import de.grossmax.portfolioandroid.app.userinterface.screens.takeawaytracker.list.TakeAwayTrackerView
import de.grossmax.portfolioandroid.app.userinterface.screens.timer.TimerView
import de.grossmax.portfolioandroid.app.userinterface.theme.PortfolioAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PortfolioAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController, startDestination = "home") {
                        composable("home") {
                            HomeView(
                                navController = navController
                            )
                        }
                        composable("timer") {
                            TimerView(
                                navController = navController,
                            )
                        }
                        composable("mcdcoupons") {
                            MCDCouponsView(
                                navHostController = navController
                            )
                        }
                        composable(
                            route = "takeawaytracker/book",
                        ) {
                            TakeAwayTrackerBookView(
                                navController = navController
                            )
                        }
                        composable("takeawaytracker") {
                            TakeAwayTrackerView(
                                navHostController = navController
                            )
                        }
                        composable("takeawaytracker/info") {
                            TakeAwayTrackerInfoView(
                                navController = navController
                            )
                        }
                        composable(
                            route = "notification_takeawaytracker",
                            deepLinks = listOf(navDeepLink { uriPattern = "https://portfolio.maax.gr/takeawaytracker/book" }),
                        ) {
//                            LaunchedEffect(key1 = true) {
  //                              val mainViewModel = get<MainViewModel>()
    //                            mainViewModel.forceNavigationState.value = true
      //                      }

                            TakeAwayTrackerBookView(
                                navController = navController,
                                customBack = "takeawaytracker"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PortfolioAndroidTheme {
        Greeting("Android")
    }
}