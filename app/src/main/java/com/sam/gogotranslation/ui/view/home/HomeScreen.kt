package com.sam.gogotranslation.ui.view.home

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sam.gogotranslation.History
import com.sam.gogotranslation.R
import com.sam.gogotranslation.repo.data.LanguageEntity
import com.sam.gogotranslation.repo.data.State
import com.sam.gogotranslation.repo.data.TranslationEntity
import com.sam.gogotranslation.ui.theme.body1
import com.sam.gogotranslation.ui.view.Clock
import com.sam.gogotranslation.ui.view.Copy
import com.sam.gogotranslation.ui.view.CustomExpandableFAB
import com.sam.gogotranslation.ui.view.LoadingIndicator
import com.sam.gogotranslation.ui.view.MyFABItem
import com.sam.gogotranslation.ui.view.noRippleClickable

private const val BOTTOM_MAX_HEIGHT_PERCENT = 0.35f
private const val SHARE_TYPE_PLAIN = "text/plain"
const val KEY_HISTORY_SELECTED = "KEY_HISTORY_SELECTED"

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val clipboardManager = LocalClipboardManager.current
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val bottomBarMaxHeight = screenHeight * BOTTOM_MAX_HEIGHT_PERCENT
    val resultState by viewModel.resultState.collectAsState()
    val translationResult by viewModel.currTranslationEntity.collectAsState(null)
    val inputLanguage by viewModel.inputLanguage.collectAsState()
    val outputLanguage by viewModel.outputLanguage.collectAsState()
    var isFabExpanded by remember { mutableStateOf(false) }
    var input by rememberSaveable {
        mutableStateOf("")
    }
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val selectedHistoryItem = savedStateHandle
        ?.getStateFlow<TranslationEntity?>(KEY_HISTORY_SELECTED, null)
        ?.collectAsState()

    val moreItem = MyFABItem(
        icon = Icons.Default.MoreVert,
        textRes = R.string.fab_more,
    )
    val optionItems = listOf(
        MyFABItem(
            icon = Icons.Default.Copy,
            textRes = R.string.fab_copy,
        ),
        MyFABItem(
            icon = Icons.Default.Share,
            textRes = R.string.fab_share,
        ),
    )

    fun closeKeyboard() {
        focusManager.clearFocus()
        keyboardController?.hide()
    }

    LaunchedEffect(resultState) {
        (resultState as? State.Error)?.let {
            Toast.makeText(context, it.throwable.message, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(selectedHistoryItem?.value) {
        val translationEntity = selectedHistoryItem?.value ?: return@LaunchedEffect
        viewModel.setCurrId(translationEntity.id)
        input = translationEntity.input
        savedStateHandle[KEY_HISTORY_SELECTED] = null
    }

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize(),
        containerColor = colorResource(id = R.color.bg_ground),
        topBar = {
            HomeAppBar(
                toHistories = {
                    navController.navigate(route = History)
                }
            )
        },
        bottomBar = {
            TranslationInputContent(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(colorResource(id = R.color.bg_primary))
                    .imePadding()
                    .navigationBarsPadding(),
                focusRequester = focusRequester,
                inputMaxHeight = bottomBarMaxHeight,
                inputLanguage = inputLanguage,
                outputLanguage = outputLanguage,
                input = input,
                onInputChanged = {
                    input = it
                },
                onClear = {
                    viewModel.clearCurrId()
                },
                onSwitchLanguage = {
                    viewModel.switchLanguage()
                },
                onSend = { input ->
                    viewModel.translate(input)
                    closeKeyboard()
                }
            )
        },
        floatingActionButton = {
            CustomExpandableFAB(
                fabButton = moreItem,
                items = optionItems,
                isExpanded = isFabExpanded,
                onExpanded = { isExpanded ->
                    isFabExpanded = isExpanded
                },
                onItemClick = ItemClick@{ item ->
                    val output = translationResult?.output.orEmpty()
                    when (item.textRes) {
                        R.string.fab_copy -> {
                            if (output.isEmpty()) return@ItemClick
                            clipboardManager.setText(AnnotatedString(output))
                        }

                        R.string.fab_share -> {
                            if (output.isEmpty()) return@ItemClick
                            val sendIntent: Intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, output)
                                type = SHARE_TYPE_PLAIN
                            }

                            Intent.createChooser(sendIntent, null).run {
                                context.startActivity(this)
                            }
                        }
                    }
                },
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .noRippleClickable {
                    isFabExpanded = false
                    closeKeyboard()
                },
        ) {
            val output = translationResult?.output.orEmpty()
            if (output.isNotEmpty()) ResultTextCard(
                modifier = Modifier
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 48.dp,
                    )
                    .fillMaxWidth(),
                text = output,
            )

            if (resultState is State.Loading) {
                LoadingIndicator(
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    thenModifier = Modifier.noRippleClickable { },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(
    toHistories: () -> Unit = {},
) {
    val title = stringResource(id = R.string.app_name)

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
            )
        },
        actions = {
            Icon(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .size(48.dp)
                    .clickable {
                        toHistories()
                    }
                    .padding(8.dp),
                imageVector = Icons.Default.Clock,
                tint = colorResource(id = R.color.text_on_bg),
                contentDescription = null,
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
    focusRequester: FocusRequester,
    inputMaxHeight: Dp,
    inputLanguage: LanguageEntity,
    outputLanguage: LanguageEntity,
    input: String = "",
    onInputChanged: (String) -> Unit = {},
    onClear: () -> Unit = {},
    onSwitchLanguage: () -> Unit = {},
    onSend: (String) -> Unit = {},
) {
    DisposableEffect(Unit) {
        focusRequester.requestFocus()
        onDispose { }
    }

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors().copy(
            containerColor = colorResource(id = R.color.bg_primary),
        ),
    ) {
        TranslationTextField(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = inputMaxHeight)
                .padding(start = 24.dp, end = 8.dp, top = 24.dp, bottom = 8.dp),
            focusRequester = focusRequester,
            text = input,
            onTextChanged = {
                onInputChanged(it)
            },
            onClear = {
                onInputChanged("")
                onClear()
            },
        )

        TranslationBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            inputLanguage = inputLanguage,
            outputLanguage = outputLanguage,
            onSwitch = onSwitchLanguage,
            onSend = {
                onSend(input)
            },
        )
    }
}

@Composable
fun TranslationTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (String) -> Unit,
    onClear: () -> Unit = {},
    focusRequester: FocusRequester,
) {
    val hint = stringResource(id = R.string.translation_hint)

    BasicTextField(
        modifier = Modifier
            .focusRequester(focusRequester),
        value = text,
        onValueChange = onTextChanged,
        minLines = 3,
        textStyle = MaterialTheme.typography.body1,
        cursorBrush = SolidColor(colorResource(id = R.color.text_on_bg)),
        decorationBox = { innerTextField ->
            Row(
                modifier = modifier,
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    innerTextField()

                    if (text.isEmpty()) Text(
                        text = hint,
                        style = MaterialTheme.typography.body1.copy(
                            color = colorResource(id = R.color.grey_35),
                        ),
                    )
                }

                if (text.isNotEmpty()) Icon(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(48.dp)
                        .clip(CircleShape)
                        .clickable {
                            onClear()
                        }
                        .padding(12.dp),
                    imageVector = Icons.Default.Clear,
                    tint = colorResource(id = R.color.text_on_bg),
                    contentDescription = null,
                )
            }
        },
    )
}

@Composable
fun TranslationBar(
    modifier: Modifier = Modifier,
    inputLanguage: LanguageEntity = LanguageEntity.ChineseTW,
    outputLanguage: LanguageEntity = LanguageEntity.English,
    onSwitch: () -> Unit = {},
    onSend: () -> Unit = {},
) {
    val inputLanguageName = stringResource(id = inputLanguage.displayNameRes)
    val outputLanguageName = stringResource(id = outputLanguage.displayNameRes)

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
                text = inputLanguageName,
                color = colorResource(id = R.color.text_on_bg),
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.size(16.dp))

            Row(
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable {
                        onSwitch()
                    }
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
                text = outputLanguageName,
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
                .clickable {
                    onSend()
                }
                .padding(8.dp),
            imageVector = Icons.AutoMirrored.Default.Send,
            contentDescription = null,
            tint = colorResource(id = R.color.text_on_bg),
        )
    }
}

@Composable
fun ResultTextCard(
    modifier: Modifier = Modifier,
    text: String,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(colorResource(id = R.color.bg_secondary))
            .padding(16.dp),
        colors = CardDefaults.cardColors().copy(
            containerColor = colorResource(id = R.color.bg_secondary),
        ),
    ) {
        SelectionContainer {
            Text(
                text = text,
                style = MaterialTheme.typography.body1,
                color = colorResource(id = R.color.text_on_bg),
            )
        }
    }
}
