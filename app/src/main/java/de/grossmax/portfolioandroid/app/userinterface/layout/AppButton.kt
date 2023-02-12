package de.grossmax.portfolioandroid.app.userinterface.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.grossmax.portfolioandroid.app.userinterface.theme.ButtonBlue
import de.grossmax.portfolioandroid.app.userinterface.theme.TextWhite

@Composable
fun AppButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Text(
        text = text,
        color = TextWhite,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
            .clickable { onClick() }
            .clip(RoundedCornerShape(10.dp))
            .background(ButtonBlue)
            .padding(vertical = 6.dp, horizontal = 15.dp)
    )

}