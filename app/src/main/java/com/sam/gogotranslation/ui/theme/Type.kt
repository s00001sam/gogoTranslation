package com.sam.gogotranslation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.sam.gogotranslation.R

val MyTypography = Typography()

val notosans = FontFamily(
    Font(R.font.notosans),
)

val montserrat = FontFamily(
    Font(R.font.montserrat_alternates),
)

val Int.nonScaledSp
    @Composable
    get() = (this / LocalDensity.current.fontScale).sp

val Typography.headline1: TextStyle
    @Composable
    get() {
        return TextStyle(
            color = colorResource(id = R.color.text_on_color),
            fontFamily = notosans,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            lineHeight = 38.sp,
        )
    }

val Typography.headline2: TextStyle
    @Composable
    get() {
        return TextStyle(
            color = colorResource(id = R.color.text_on_color),
            fontFamily = notosans,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 32.sp,
        )
    }

val Typography.headline3: TextStyle
    @Composable
    get() {
        return TextStyle(
            color = colorResource(id = R.color.text_on_color),
            fontFamily = notosans,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 28.sp,
        )
    }

val Typography.headline4: TextStyle
    @Composable
    get() {
        return TextStyle(
            color = colorResource(id = R.color.text_on_color),
            fontFamily = notosans,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            lineHeight = 26.sp,
        )
    }

val Typography.headline5: TextStyle
    @Composable
    get() {
        return TextStyle(
            color = colorResource(id = R.color.text_on_color),
            fontFamily = notosans,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            lineHeight = 24.sp,
        )
    }

val Typography.headline6: TextStyle
    @Composable
    get() {
        return TextStyle(
            color = colorResource(id = R.color.text_on_color),
            fontFamily = notosans,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            lineHeight = 22.sp,
        )
    }

val Typography.body1: TextStyle
    @Composable
    get() {
        return TextStyle(
            color = colorResource(id = R.color.text_on_color),
            fontFamily = notosans,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
        )
    }

val Typography.body2: TextStyle
    @Composable
    get() {
        return TextStyle(
            color = colorResource(id = R.color.text_on_color),
            fontFamily = notosans,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 22.sp,
        )
    }

val Typography.label: TextStyle
    @Composable
    get() {
        return TextStyle(
            color = colorResource(id = R.color.text_on_color),
            fontFamily = notosans,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            lineHeight = 20.sp,
        )
    }

val Typography.appBarTitle: TextStyle
    @Composable
    get() {
        return TextStyle(
            color = colorResource(id = R.color.text_on_color),
            fontFamily = montserrat,
            fontWeight = FontWeight.Bold,
            fontSize = 28.nonScaledSp,
            lineHeight = 36.nonScaledSp,
        )
    }
