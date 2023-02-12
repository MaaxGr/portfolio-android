package de.grossmax.portfolioandroid.app.businesslogic.screens.takeawaytracker

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ImMemoryTakeAwayTrackerRepoImpl : TakeAwayTrackerRepo {

    private val takeaways = mutableListOf<TakeAwayTrackerRepo.TakeAway>()
    private val sharedFlow = MutableStateFlow<List<TakeAwayTrackerRepo.TakeAway>>(listOf())

    override suspend fun addTakeAway(draft: TakeAwayTrackerRepo.TakeAwayDraft) {
        val id = takeaways.size + 1

        if (draft.price.toDoubleOrNull() == null) {
            throw IllegalArgumentException("Price is not a double")
        }

        takeaways.add(
            TakeAwayTrackerRepo.TakeAway(
                id = id,
                what = draft.what,
                price = draft.price.toDouble(),
                createdAt = System.currentTimeMillis()
            )
        )
        sharedFlow.emit(takeaways)
    }

    override suspend fun getTakeAways(): StateFlow<List<TakeAwayTrackerRepo.TakeAway>> {
        return sharedFlow
    }

}