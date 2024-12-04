package com.sam.gogotranslation.ui.view

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Icons.Filled.Rotate: ImageVector
    get() {
        if (_Rotate_90_degrees_ccw != null) {
            return _Rotate_90_degrees_ccw!!
        }
        _Rotate_90_degrees_ccw = ImageVector.Builder(
            name = "Rotate_90_degrees_ccw",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(520f, 880f)
                quadToRelative(-51f, 0f, -100f, -14f)
                reflectiveQuadToRelative(-92f, -42f)
                lineToRelative(58f, -58f)
                quadToRelative(31f, 17f, 65f, 25.5f)
                reflectiveQuadToRelative(69f, 8.5f)
                quadToRelative(117f, 0f, 198.5f, -81.5f)
                reflectiveQuadTo(800f, 520f)
                reflectiveQuadToRelative(-81.5f, -198.5f)
                reflectiveQuadTo(520f, 240f)
                horizontalLineToRelative(-6f)
                lineToRelative(62f, 62f)
                lineToRelative(-56f, 58f)
                lineToRelative(-160f, -160f)
                lineToRelative(160f, -160f)
                lineToRelative(56f, 58f)
                lineToRelative(-62f, 62f)
                horizontalLineToRelative(6f)
                quadToRelative(150f, 0f, 255f, 105f)
                reflectiveQuadToRelative(105f, 255f)
                quadToRelative(0f, 75f, -28.5f, 140.5f)
                reflectiveQuadToRelative(-77f, 114f)
                reflectiveQuadToRelative(-114f, 77f)
                reflectiveQuadTo(520f, 880f)
                moveTo(280f, 760f)
                lineTo(40f, 520f)
                lineToRelative(240f, -240f)
                lineToRelative(240f, 240f)
                close()
                moveToRelative(0f, -114f)
                lineToRelative(126f, -126f)
                lineToRelative(-126f, -126f)
                lineToRelative(-126f, 126f)
                close()
                moveToRelative(0f, -126f)
            }
        }.build()
        return _Rotate_90_degrees_ccw!!
    }

private var _Rotate_90_degrees_ccw: ImageVector? = null

