package de.grossmax.portfolioandroid.app.userinterface.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import de.grossmax.portfolioandroid.app.userinterface.theme.AquaBlue
import de.grossmax.portfolioandroid.app.userinterface.theme.ButtonBlue
import de.grossmax.portfolioandroid.app.userinterface.theme.DeepBlue

@Composable
fun AppBottomNavigation(
    items: List<BottomMenuContent>,
    modifier: Modifier = Modifier,
    activeHighlightColor: Color = ButtonBlue,
    activeTextColor: Color = Color.White,
    inactiveTextColor: Color = AquaBlue,
    initialSelectedItemIndex: Int = 0
) {

    var selectedItemIndex by remember {
        mutableStateOf(initialSelectedItemIndex)
    }

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(DeepBlue)
            .padding(15.dp)
    ) {

        items.forEachIndexed { index, item ->

            AppBottomNavigationItem(
                item = item,
                isSelected = selectedItemIndex == index,
                activeHighlightColor = activeHighlightColor,
                activeTextColor = activeTextColor,
                inactiveTextColor = inactiveTextColor,
                onItemClick = {
                    selectedItemIndex = index
                }
            )

        }

    }

}

@Composable
fun AppBottomNavigationItem(
    item: BottomMenuContent,
    isSelected: Boolean = false,
    activeHighlightColor: Color = ButtonBlue,
    activeTextColor: Color = Color.White,
    inactiveTextColor: Color = AquaBlue,
    onItemClick: () -> Unit
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable { onItemClick() }
    ) {

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(
                    if (isSelected) {
                        activeHighlightColor
                    } else Color.Transparent
                )
                .padding(10.dp)
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = item.title,
                tint = if (isSelected) activeTextColor else inactiveTextColor,
                modifier = Modifier
                    .size(20.dp)
            )
        }

        Text(
            text = item.title,
            color = if (isSelected) activeTextColor else inactiveTextColor
        )

    }

}


data class BottomMenuContent(
    val title: String,
    val icon: ImageVector
)