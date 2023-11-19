package com.app.mymobiusdemo.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Observer
import com.spotify.mobius.android.LiveQueue

@Composable
fun <T> LiveQueue<T>.observeWithDisposable(onViewEffect: (T) -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(this, lifecycleOwner) {
        val observer = Observer<T> { onViewEffect(it) }
        setObserver(lifecycleOwner, observer)
        onDispose {
            clearObserver()
        }
    }
}