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

val Icons.Filled.Clock: ImageVector
    get() {
        if (_CounterClockwiseClock != null) {
            return _CounterClockwiseClock!!
        }
        _CounterClockwiseClock = ImageVector.Builder(
            name = "com.sam.gogotranslation.ui.view.getCounterClockwiseClock",
            defaultWidth = 15.dp,
            defaultHeight = 15.dp,
            viewportWidth = 15f,
            viewportHeight = 15f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(13.15f, 7.49998f)
                curveTo(13.15f, 4.6646f, 10.9402f, 1.85f, 7.5f, 1.85f)
                curveTo(4.7217f, 1.85f, 3.3485f, 3.9064f, 2.7633f, 5f)
                horizontalLineTo(4.5f)
                curveTo(4.7761f, 5f, 5f, 5.2239f, 5f, 5.5f)
                curveTo(5f, 5.7761f, 4.7761f, 6f, 4.5f, 6f)
                horizontalLineTo(1.5f)
                curveTo(1.2239f, 6f, 1f, 5.7761f, 1f, 5.5f)
                verticalLineTo(2.5f)
                curveTo(1f, 2.2239f, 1.2239f, 2f, 1.5f, 2f)
                curveTo(1.7761f, 2f, 2f, 2.2239f, 2f, 2.5f)
                verticalLineTo(4.31318f)
                curveTo(2.7045f, 3.0713f, 4.3341f, 0.85f, 7.5f, 0.85f)
                curveTo(11.5628f, 0.85f, 14.15f, 4.1854f, 14.15f, 7.5f)
                curveTo(14.15f, 10.8146f, 11.5628f, 14.15f, 7.5f, 14.15f)
                curveTo(5.5562f, 14.15f, 3.9378f, 13.3808f, 2.7855f, 12.2084f)
                curveTo(2.1685f, 11.5806f, 1.6867f, 10.839f, 1.3582f, 10.0407f)
                curveTo(1.2531f, 9.7854f, 1.3749f, 9.4931f, 1.6302f, 9.3881f)
                curveTo(1.8856f, 9.283f, 2.1778f, 9.4048f, 2.2829f, 9.6601f)
                curveTo(2.5637f, 10.3425f, 2.975f, 10.9745f, 3.4987f, 11.5074f)
                curveTo(4.4705f, 12.4963f, 5.835f, 13.15f, 7.5f, 13.15f)
                curveTo(10.9402f, 13.15f, 13.15f, 10.3354f, 13.15f, 7.5f)
                close()
                moveTo(7.5f, 4.00001f)
                curveTo(7.7761f, 4f, 8f, 4.2239f, 8f, 4.5f)
                verticalLineTo(7.29291f)
                lineTo(9.85355f, 9.14646f)
                curveTo(10.0488f, 9.3417f, 10.0488f, 9.6583f, 9.8536f, 9.8536f)
                curveTo(9.6583f, 10.0488f, 9.3417f, 10.0488f, 9.1464f, 9.8536f)
                lineTo(7.14645f, 7.85357f)
                curveTo(7.0527f, 7.7598f, 7f, 7.6326f, 7f, 7.5f)
                verticalLineTo(4.50001f)
                curveTo(7f, 4.2239f, 7.2239f, 4f, 7.5f, 4f)
                close()
            }
        }.build()
        return _CounterClockwiseClock!!
    }

private var _CounterClockwiseClock: ImageVector? = null
