package com.app.mymobiusdemo.animation

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale

@Composable
fun PulsatingEffect(pulseFraction: Float = 1.2f, content: @Composable () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    
    val scale by infiniteTransition.animateFloat(initialValue = 1f, targetValue = pulseFraction, animationSpec = infiniteRepeatable(
        animation = tween(500),
        repeatMode = RepeatMode.Reverse
    ),
        label = ""
    )

    Box(modifier = Modifier.scale(scale)) {
        content()
    }
}