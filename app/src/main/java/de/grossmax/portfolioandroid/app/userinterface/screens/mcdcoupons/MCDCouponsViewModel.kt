package de.grossmax.portfolioandroid.app.userinterface.screens.mcdcoupons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class MCDCouponsViewModel: ViewModel() {

    val uiStateFlow: MutableStateFlow<UIState> = MutableStateFlow(
        UIState(
            loading = false,
            coupons = emptyList()
        )
    )

    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(
                json = kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    fun loadCoupons() {
        viewModelScope.launch {
            uiStateFlow.value = uiStateFlow.value.copy(loading = true)

            val response: LoadCouponsResponse = httpClient.get(
                urlString = "https://www.mcdonalds.de/gutscheine/json/coupons.json"
            ).body()

            uiStateFlow.value = uiStateFlow.value.copy(
                loading = false,
                coupons = response.coupons
            )

        }

    }

    data class UIState(
        val loading: Boolean,
        val coupons: List<MCDonaldsCoupon> = emptyList()
    )

    @Serializable
    data class LoadCouponsResponse(
        val coupons: List<MCDonaldsCoupon>
    )

    @Serializable
    data class MCDonaldsCoupon(
        val id: Int,
        val campaignId: String,
        val description: String,
        val subDescription: String,
        val shortSubDescription: String,
        val price: String,
        val filter: List<String>,
        val images: MCDonaldsCouponImage
    )

    @Serializable
    data class MCDonaldsCouponImage(
        val default: MCDonaldsCouponImageDefault
    )

    @Serializable
    data class MCDonaldsCouponImageDefault(
        val name: String
    )

}