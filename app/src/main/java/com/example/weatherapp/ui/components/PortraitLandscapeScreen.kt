package com.example.weatherapp.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

/**
 * Custom Scaffold that repositions provided lazy items depending on the local configuration of portrait or landscape
 * */
@Composable
fun PortraitLandscapeScreen(
    modifier: Modifier = Modifier,
    lazyPrimary: (LazyListScope.() -> Unit),
    lazySecondary: (LazyListScope.() -> Unit),
) {

    when(LocalConfiguration.current.orientation){
        Configuration.ORIENTATION_LANDSCAPE -> {
            LandscapeScreen(
                modifier = modifier,
                lazyPrimary = lazyPrimary,
                lazySecondary = lazySecondary,
            )
        }
        Configuration.ORIENTATION_PORTRAIT,
        Configuration.ORIENTATION_SQUARE,
        Configuration.ORIENTATION_UNDEFINED -> {
            PortraitScreen(
                modifier = modifier,
                lazyPrimary = lazyPrimary,
                lazySecondary = lazySecondary,
            )
        }
    }
}

@Composable
private fun PortraitScreen(
    modifier: Modifier = Modifier,
    lazyPrimary: (LazyListScope.() -> Unit),
    lazySecondary: (LazyListScope.() -> Unit),
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        lazyPrimary()
        lazySecondary()
    }
}

@Composable
private fun LandscapeScreen(
    modifier: Modifier = Modifier,
    lazyPrimary: (LazyListScope.() -> Unit),
    lazySecondary: (LazyListScope.() -> Unit),
) {
    Row() {
        LazyColumn(
            modifier = modifier
                .weight(1f)
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            lazyPrimary()
        }
        LazyColumn(
            modifier = modifier
                .weight(1f)
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            lazySecondary()
        }
    }
}