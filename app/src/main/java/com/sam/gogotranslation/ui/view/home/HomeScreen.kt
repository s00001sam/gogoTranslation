package com.sam.gogotranslation.ui.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sam.gogotranslation.R

@Composable
fun HomeScreen(
    navController: NavController,
) {
    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize(),
        containerColor = colorResource(id = R.color.bg_ground),
        topBar = {
            HomeAppBar()
        },
        bottomBar = {
            TranslationInputContent()
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar() {
    val title = stringResource(id = R.string.app_name)

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors().copy(
            containerColor = colorResource(id = R.color.primary_color)
        ),
    )
}

@Composable
fun TranslationInputContent(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
            .background(colorResource(id = R.color.bg_primary))
            .navigationBarsPadding(),
        colors = CardDefaults.cardColors().copy(
            containerColor = colorResource(id = R.color.bg_primary),
        ),
    ) {
        // fake TextField
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .height(120.dp),
            )

            Spacer(modifier = Modifier.size(8.dp))

            Icon(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .clickable { }
                    .padding(8.dp),
                imageVector = Icons.Default.Clear,
                tint = colorResource(id = R.color.text_on_bg),
                contentDescription = null,
            )
        }

        TranslationBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )
    }
}

@Composable
fun TranslationBar(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
                .clip(CircleShape)
                .background(colorResource(id = R.color.bg_secondary)),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f),
                text = "中文",
                color = colorResource(id = R.color.text_on_bg),
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.size(16.dp))

            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { }
                    .padding(8.dp),
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = colorResource(id = R.color.text_on_bg),
                )

                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    tint = colorResource(id = R.color.text_on_bg),
                )
            }

            Spacer(modifier = Modifier.size(16.dp))

            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f),
                text = "英文",
                color = colorResource(id = R.color.text_on_bg),
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        Icon(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .clickable { }
                .padding(8.dp),
            imageVector = Icons.AutoMirrored.Default.Send,
            contentDescription = null,
            tint = colorResource(id = R.color.text_on_bg),
        )
    }
}

@Composable
fun AddFAB(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        modifier = modifier,
        shape = CircleShape,
        containerColor = Color.White,
        onClick = onClick,
    ) {
        Icon(
            modifier = Modifier.size(28.dp),
            imageVector = Icons.Default.Add,
            tint = colorResource(id = R.color.primary_dark),
            contentDescription = null,
        )
    }
}
