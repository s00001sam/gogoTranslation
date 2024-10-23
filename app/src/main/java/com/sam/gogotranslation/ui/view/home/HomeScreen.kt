package com.sam.gogotranslation.ui.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sam.gogotranslation.R
import com.sam.gogotranslation.ui.theme.body1
import com.sam.gogotranslation.ui.view.noRippleClickable

private const val BOTTOM_MAX_HEIGHT_PERCENT = 0.35f

@Composable
fun HomeScreen(
    navController: NavController,
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val bottomBarMaxHeight = screenHeight * BOTTOM_MAX_HEIGHT_PERCENT
    val translationResult =
        "dlkas;ldjask\ndkajshdliaskj\ndskajhdkjashd\nsdlkjaslkdajsdklajsldkasjlkdja\n" +
            "dkajshdliaskj\n" +
            "dskajhdkjashd\n" +
            "sdlkjaslkdajsdklajsldkasjlkdja\n" +
            "dkajshdliaskj\n" +
            "dskajhdkjashd\n" +
            "sdlkjaslkdajsdklajsldkasjlkdja\n" +
            "dkajshdliaskj\n" +
            "dskajhdkjashd\n" +
            "sdlkjaslkdajsdklajsldkasjlkdja\n" +
            "dkajshdliaskj\n" +
            "dskajhdkjashd\n" +
            "sdlkjaslkdajsdklajsldkasjlkdja\n" +
            "dkajshdliaskj\n" +
            "dskajhdkjashd\n" +
            "sdlkjaslkdajsdklajsldkasjlkdja\n" +
            "dkajshdliaskj\n" +
            "dskajhdkjashd\n" +
            "sdlkjaslkdajsdklajsldkasjlkdja\n" +
            "dkajshdliaskj\n" +
            "dskajhdkjashd\n" +
            "sdlkjaslkdajsdklajsldkasjlkdja\n" +
            "dkajshdliaskj\n" +
            "dskajhdkjashd\n" +
            "sdlkjaslkdajsdklajsldkasjlkdja\n" +
            "dkajshdliaskj\n" +
            "dskajhdkjashd\n" +
            "sdlkjaslkdajsdklajsldkasjlkdja"

    fun closeKeyboard() {
        focusManager.clearFocus()
        keyboardController?.hide()
    }

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .fillMaxSize(),
        containerColor = colorResource(id = R.color.bg_ground),
        topBar = {
            HomeAppBar()
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
                onSend = {
                    // TODO: translate by Gemini API
                    closeKeyboard()
                }
            )
        },
        floatingActionButton = {
            MoreFAB(
                modifier = Modifier,
                onClick = {
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .noRippleClickable {
                    closeKeyboard()
                },
        ) {
            ResultTextCard(
                modifier = Modifier
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 48.dp,
                    )
                    .fillMaxWidth(),
                text = translationResult,
            )
        }
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
    focusRequester: FocusRequester,
    inputMaxHeight: Dp,
    onSend: (String) -> Unit = {},
) {
    var input by rememberSaveable {
        mutableStateOf("")
    }

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
            text = input,
            onTextChanged = {
                input = it
            },
            focusRequester = focusRequester,
        )

        TranslationBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
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
    focusRequester: FocusRequester,
) {
    val hint = stringResource(id = R.string.translation_hint)
    var currKeyboardLanguage by rememberSaveable { mutableStateOf("en") }

    BasicTextField(
        modifier = Modifier
            .focusRequester(focusRequester),
        value = text,
        onValueChange = onTextChanged,
        minLines = 3,
        textStyle = MaterialTheme.typography.body1,
        cursorBrush = SolidColor(colorResource(id = R.color.text_on_bg)),
        keyboardOptions = KeyboardOptions.Default.copy(
            hintLocales = LocaleList(Locale(currKeyboardLanguage)),
        ),
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
                            onTextChanged("")

                            if (currKeyboardLanguage == "en") {
                                currKeyboardLanguage = "zh"
                            } else {
                                currKeyboardLanguage = "en"
                            }
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
    onSend: () -> Unit = {},
) {
    val chineseStr = stringResource(id = R.string.language_chinese)
    val englishStr = stringResource(id = R.string.language_english)

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
                text = chineseStr,
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
                text = englishStr,
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

@Composable
fun MoreFAB(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        modifier = modifier,
        shape = CircleShape,
        containerColor = colorResource(id = R.color.bg_primary),
        contentColor = colorResource(id = R.color.text_on_bg),
        onClick = onClick,
    ) {
        Icon(
            modifier = Modifier.size(28.dp),
            imageVector = Icons.Default.MoreVert,
            contentDescription = null,
        )
    }
}
