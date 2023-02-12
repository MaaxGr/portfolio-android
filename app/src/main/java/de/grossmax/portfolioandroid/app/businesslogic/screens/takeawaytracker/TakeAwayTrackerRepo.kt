package de.grossmax.portfolioandroid.app.businesslogic.screens.takeawaytracker

import kotlinx.coroutines.flow.StateFlow

interface TakeAwayTrackerRepo {

    data class TakeAwayDraft(
        val what: String,
        val price: String,
    )

    data class TakeAway(
        val id: Int,
        val what: String,
        val price: Double,
        val createdAt: Long
    )

    suspend fun addTakeAway(draft: TakeAwayDraft)

    suspend fun getTakeAways(): StateFlow<List<TakeAway>>

}