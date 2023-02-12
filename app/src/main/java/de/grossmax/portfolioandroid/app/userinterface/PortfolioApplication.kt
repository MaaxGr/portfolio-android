package de.grossmax.portfolioandroid.app.userinterface

import android.app.Application
import de.grossmax.portfolioandroid.app.businesslogic.screens.takeawaytracker.ImMemoryTakeAwayTrackerRepoImpl
import de.grossmax.portfolioandroid.app.businesslogic.screens.takeawaytracker.TakeAwayTrackerRepo
import de.grossmax.portfolioandroid.app.userinterface.screens.takeawaytracker.TakeAwayTrackerModule
import de.grossmax.portfolioandroid.app.userinterface.screens.takeawaytracker.TakeAwayTrackerUtils
import de.grossmax.portfolioandroid.app.userinterface.screens.takeawaytracker.book.TakeAwayTrackerBookViewModel
import de.grossmax.portfolioandroid.app.userinterface.screens.takeawaytracker.list.TakeAwayTrackerListViewModel
import de.grossmax.portfolioandroid.app.userinterface.screens.timer.TimerServiceModel
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class PortfolioApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            val mainModule = module {
                // TODO check if viewmodel<>?
                single { TimerServiceModel() }
                single { MainViewModel() }
                viewModel { TakeAwayTrackerBookViewModel() }
                viewModel { TakeAwayTrackerListViewModel() }

                // repos
                single<TakeAwayTrackerRepo> { ImMemoryTakeAwayTrackerRepoImpl() }

                // other
                single { TakeAwayTrackerModule() }

            }
            modules(mainModule)
        }

        TakeAwayTrackerUtils.createNotificationChannel(applicationContext)
        get<TakeAwayTrackerModule>().scheduleReminder(applicationContext)
    }
}