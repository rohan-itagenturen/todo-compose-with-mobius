package com.app.mymobiusdemo.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.app.mymobiusdemo.R
import com.app.mymobiusdemo.animation.PulsatingEffect
import com.app.mymobiusdemo.ui.AppScreens
import com.app.mymobiusdemo.ui.theme.MyMobiusDemoTheme
import kotlinx.coroutines.delay

private const val SPLASH_TIME_OUT = 3000L

@Composable
fun SplashScreen(onNavigateTo: (String) -> Unit) {

    SplashUI(onNavigateTo = onNavigateTo)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SplashUI(modifier: Modifier = Modifier, onNavigateTo: (String) -> Unit) {
    Scaffold {
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(it),
            contentAlignment = Alignment.Center
        ) {
            val currentOnTimeOut by rememberUpdatedState(newValue = onNavigateTo)

            LaunchedEffect(key1 = true, block = {
                delay(SPLASH_TIME_OUT)
                currentOnTimeOut(AppScreens.TaskList.name)
            })

            PulsatingEffect {
                Text(
                    text = stringResource(id = R.string.app_name),
                    color = Color.Companion.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    MyMobiusDemoTheme {
        SplashUI(onNavigateTo = { })
    }
}