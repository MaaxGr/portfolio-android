package de.grossmax.portfolioandroid.app.userinterface.screens.mcdcoupons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Smartphone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import de.grossmax.portfolioandroid.app.userinterface.layout.AppTopBarWithBackButton
import de.grossmax.portfolioandroid.app.userinterface.theme.Beige3
import de.grossmax.portfolioandroid.app.userinterface.theme.ButtonBlue
import de.grossmax.portfolioandroid.app.userinterface.theme.DeepBlue
import de.grossmax.portfolioandroid.app.userinterface.theme.TextWhite

@Composable
fun MCDCouponsView(
    navHostController: NavHostController
) {
    Column(
        modifier = Modifier
            .background(DeepBlue)
            .fillMaxSize()
    ) {

        AppTopBarWithBackButton(title = "MCDonalds Coupons", navController = navHostController)

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 15.dp)
        ) {
            MCDCouponsViewContent()
        }
    }

}

@Composable
fun MCDCouponsViewContent(
    viewModel: MCDCouponsViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.loadCoupons()
    }

    val uiState: MCDCouponsViewModel.UIState by viewModel.uiStateFlow.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(1),
            contentPadding = PaddingValues(start = 7.5.dp, end = 7.5.dp, bottom = 100.dp),
            modifier = Modifier.fillMaxHeight()
        ) {
            items(uiState.coupons.size) {
                val coupon = uiState.coupons[it]
                SingleCoupon(coupon = coupon)
            }
        }
    }
}

@Composable
private fun SingleCoupon(coupon: MCDCouponsViewModel.MCDonaldsCoupon) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(7.5.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(15.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = coupon.description,
                style = MaterialTheme.typography.h2,
                lineHeight = 26.sp,
                color = Color.Black,
                modifier = Modifier
            )
            Text(
                text = coupon.shortSubDescription,
                style = MaterialTheme.typography.body1,
                lineHeight = 26.sp,
                modifier = Modifier
            )

            AsyncImage(
                model = "https://mcdportalmediast1.blob.core.windows.net/mcd-portal-prod-backend/coupons/images/${coupon.campaignId}/${coupon.images.default.name}",
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }



/*        Icon(
            imageVector = feature.icon,
            contentDescription = feature.title,
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.BottomStart)
        )*/

        val exclusive = coupon.filter.contains("exklusiv")

        Box(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .clip(RoundedCornerShape(10.dp))
                .background(ButtonBlue)
                .padding(vertical = 6.dp, horizontal = 15.dp)
        ) {

            val numberOnly = coupon.images.default.name.replace("\\D".toRegex(), "")
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = if (exclusive) {
                        "Exklusiv in der App"
                    } else {
                        "Nr. $numberOnly"
                    },
                    color = TextWhite,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                )
            }

        }


        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .clip(RoundedCornerShape(10.dp))
                .background(Beige3)
                .padding(vertical = 6.dp, horizontal = 15.dp)
        ) {


            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = coupon.price,
                    color = TextWhite,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                )

                if (coupon.filter.contains("exklusiv")) {
                    Icon(
                        imageVector = Icons.Default.Smartphone,
                        contentDescription = "Exclusive in App",
                        tint = TextWhite,
                        modifier = Modifier
                            .padding(start = 5.dp)
                    )
                }

            }

        }



    }
}