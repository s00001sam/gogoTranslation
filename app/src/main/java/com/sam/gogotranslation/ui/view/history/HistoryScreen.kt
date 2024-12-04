package com.sam.gogotranslation.ui.view.history

import androidx.compose.animation.core.tween
import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.gestures.animateTo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sam.gogotranslation.R
import com.sam.gogotranslation.repo.data.State
import com.sam.gogotranslation.repo.data.TranslationEntity
import com.sam.gogotranslation.ui.theme.body1
import com.sam.gogotranslation.ui.theme.label
import com.sam.gogotranslation.ui.view.home.KEY_HISTORY_SELECTED
import com.sam.gogotranslation.utils.HISTORY_EDIT_TIME_FORMATTER
import com.sam.gogotranslation.utils.format
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.math.roundToInt

private const val DIALOG_HEIGHT_PERCENTAGE = 0.9f

private enum class HorizontalDragValue { Settled, Deleted }

@Composable
fun HistoryScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HistoryViewModel = hiltViewModel(),
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp
    val screenWidth = configuration.screenWidthDp
    val dialogHeight by remember {
        derivedStateOf {
            minOf(screenWidth, screenHeight) * DIALOG_HEIGHT_PERCENTAGE
        }
    }
    val translations by viewModel.translations.collectAsState(emptyList())
    val deleteResult by viewModel.deleteResult.collectAsState(State.Empty)

    LaunchedEffect(deleteResult) {
        viewModel.clearDeleteId()
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .size(dialogHeight.dp)
            .background(color = colorResource(R.color.bg_ground))
            .padding(16.dp),
    ) {
        when {
            translations.isNotEmpty() -> {
                LazyColumn {
                    items(
                        count = translations.size,
                    ) { index ->
                        val item = translations[index]

                        TranslationItemView(
                            modifier = Modifier
                                .fillMaxWidth(),
                            item = item,
                            onCardClick = { entity ->
                                runCatching {
                                    val backStackEntry = navController.previousBackStackEntry
                                    val savedStateHandle = backStackEntry?.savedStateHandle
                                    savedStateHandle?.set(KEY_HISTORY_SELECTED, entity)
                                }.onFailure(Timber::e)

                                navController.navigateUp()
                            },
                            onDelete = { entity ->
                                viewModel.deleteTranslation(entity.id)
                            },
                        )

                        if (index < translations.size - 1) Spacer(
                            modifier = Modifier.size(8.dp),
                        )
                    }
                }
            }

            else -> {
                Text(
                    modifier = Modifier
                        .align(Alignment.TopCenter),
                    text = stringResource(R.string.history_empty),
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TranslationItemView(
    modifier: Modifier = Modifier,
    item: TranslationEntity,
    onCardClick: (TranslationEntity) -> Unit = {},
    onDelete: (TranslationEntity) -> Unit = {},
) {
    val density = LocalDensity.current
    val scope = rememberCoroutineScope()
    val textMeasurer = rememberTextMeasurer()
    val lineHeight = textMeasurer.measure(
        text = "Test\nTest",
        style = MaterialTheme.typography.body1,
    ).size.height
    val itemHeight = with(density) { lineHeight.toDp() }
    val deleteAnchor = with(density) { 60.dp.toPx() }
    val anchors = DraggableAnchors {
        HorizontalDragValue.Settled at 0f
        HorizontalDragValue.Deleted at -deleteAnchor
    }
    val decayAnimationSpec = rememberSplineBasedDecay<Float>()
    val state = remember {
        AnchoredDraggableState(
            initialValue = HorizontalDragValue.Settled,
            anchors = anchors,
            positionalThreshold = { totalDistance ->
                totalDistance * 0.6f
            },
            velocityThreshold = { 0.3f },
            snapAnimationSpec = tween(),
            decayAnimationSpec = decayAnimationSpec,
        )
    }
    val inputLanguageName = stringResource(item.inputLanguage.displayNameRes)
    val outputLanguageName = stringResource(item.outputLanguage.displayNameRes)
    val languageDisplay = stringResource(
        R.string.history_language,
        inputLanguageName,
        outputLanguageName,
    )
    val editTimeStr = item.editZoneDateTime?.format(HISTORY_EDIT_TIME_FORMATTER).orEmpty()

    SideEffect { state.updateAnchors(anchors) }

    Box(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier
                .width(60.dp)
                .fillMaxHeight()
                .align(Alignment.CenterEnd)
                .clickable {
                    onDelete(item)
                    scope.launch { state.animateTo(HorizontalDragValue.Settled) }
                },
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                imageVector = Icons.Default.Delete,
                tint = Color.Red,
                contentDescription = "Delete",
            )
        }

        Card(
            modifier = modifier
                .offset {
                    IntOffset(
                        x = state
                            .requireOffset()
                            .roundToInt(),
                        y = 0,
                    )
                }
                .fillMaxSize()
                .anchoredDraggable(state, Orientation.Horizontal)
                .clip(RoundedCornerShape(16.dp))
                .clickable {
                    onCardClick(item)
                },
            colors = CardDefaults.cardColors().copy(
                containerColor = colorResource(R.color.bg_secondary),
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    modifier = Modifier
                        .background(
                            color = colorResource(R.color.bg_decoration),
                            shape = CircleShape,
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp),
                    text = languageDisplay,
                    style = MaterialTheme.typography.label,
                )

                Spacer(Modifier.size(8.dp))

                Box(
                    modifier = Modifier
                        .height(itemHeight)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    Text(
                        text = item.input,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.body1,
                    )
                }

                Spacer(Modifier.size(8.dp))

                Text(
                    modifier = Modifier
                        .align(Alignment.End),
                    text = editTimeStr,
                    style = MaterialTheme.typography.label,
                )
            }
        }
    }
}

@Preview
@Composable
fun TranslationItemViewPreview() {
    TranslationItemView(
        modifier = Modifier.fillMaxWidth(),
        item = TranslationEntity(
            input = "d,jsahdjkashdjak,mda.sdnas,dnasdnaskdnd,jsahdjkashdjak,mda.sdnas,dnasdnaskdnd,jsahdjkashdjak,mda.sdnas,dnasdnaskdn"
        ),
    )
}
