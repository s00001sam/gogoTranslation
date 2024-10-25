package com.sam.gogotranslation.ui.view

import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sam.gogotranslation.R

@Composable
fun Modifier.noRippleClickable(onClick: () -> Unit) =
    this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }

@Composable
fun Modifier.noBoundClickable(onClick: () -> Unit) =
    this.clickable(
        indication = ripple(bounded = false),
        interactionSource = remember { MutableInteractionSource() },
    ) {
        onClick()
    }

data class MyFABItem(
    val icon: ImageVector,
    @StringRes val textRes: Int,
)

@Composable
fun CustomExpandableFAB(
    modifier: Modifier = Modifier,
    fabButton: MyFABItem,
    items: List<MyFABItem>,
    isExpanded: Boolean,
    onItemClick: (MyFABItem) -> Unit,
    onExpanded: (Boolean) -> Unit,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.bg_primary),
        ),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
    ) {
        Row(
            modifier = Modifier
                .background(color = colorResource(id = R.color.bg_secondary))
                .animateContentSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (isExpanded) items.forEach { item ->
                FABContent(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    item = item,
                    paddingValues = 12.dp,
                    onClick = {
                        onItemClick(item)
                    }
                )
            }

            // The FAB main button
            FABContent(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(color = colorResource(id = R.color.bg_primary)),
                paddingValues = 16.dp,
                item = fabButton,
                onClick = {
                    onExpanded(!isExpanded)
                }
            )
        }
    }

}

@Composable
fun FABContent(
    modifier: Modifier = Modifier,
    paddingValues: Dp = 8.dp,
    item: MyFABItem,
    onClick: (() -> Unit)? = null,
) {
    Icon(
        modifier = modifier
            .clickable(
                enabled = onClick != null,
                onClick = {
                    onClick?.invoke()
                }
            )
            .padding(paddingValues)
            .size(24.dp),
        imageVector = item.icon,
        contentDescription = stringResource(id = item.textRes),
    )
}
