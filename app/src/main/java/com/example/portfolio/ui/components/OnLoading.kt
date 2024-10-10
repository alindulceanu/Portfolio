package com.example.portfolio.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.ProgressIndicatorDefaults.CircularStrokeWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun OnLoading(
    modifier: Modifier = Modifier,
    size: Dp = 50.dp,
    sweepAngle: Float = 90f,
    color: Color = colorScheme.primary,
    strokeWidth: Dp = CircularStrokeWidth,
) {
    val transition = rememberInfiniteTransition(label = "Loading")

    val currentArcState by transition.animateValue(
        0,
        360,
        Int.VectorConverter,
        infiniteRepeatable(
            animation = tween(
                durationMillis = 1100,
                easing = LinearEasing
            )
        ),
        label = "xD",
    )

    val stroke = with(LocalDensity.current) {
        Stroke(strokeWidth.toPx(), cap = StrokeCap.Round )
    }
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Canvas(
            modifier = modifier
                .progressSemantics()
                .size(size)
                .padding(strokeWidth / 2)
                .align(Center)
        ) {
            drawCircle(color = LightGray, style = stroke)

            drawArc(
                color = color,
                startAngle = currentArcState.toFloat() - 90,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = stroke
            )
        }
    }
}