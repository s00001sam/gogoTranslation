package com.sam.gogotranslation.ui.view.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sam.gogotranslation.R
import com.sam.gogotranslation.repo.data.TranslationEntity
import com.sam.gogotranslation.ui.theme.body1

private const val DIALOG_HEIGHT_PERCENTAGE = 0.9f

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

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .size(dialogHeight.dp)
            .background(color = colorResource(R.color.bg_primary))
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

@Composable
fun TranslationItemView(
    modifier: Modifier = Modifier,
    item: TranslationEntity,
) {
    val density = LocalDensity.current
    val textMeasurer = rememberTextMeasurer()
    val lineHeight = textMeasurer.measure(
        text = "Test\nTest",
        style = MaterialTheme.typography.body1,
    ).size.height
    val lineHeightDp = with(density) { lineHeight.toDp() }

    Card(
        modifier = modifier
            .clickable {  },
        colors = CardDefaults.cardColors().copy(
            containerColor = colorResource(R.color.bg_secondary),
        ),
    ) {
        Text(
            modifier = Modifier
                .padding(16.dp)
                .heightIn(min = lineHeightDp),
            text = item.input,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.body1,
        )
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
