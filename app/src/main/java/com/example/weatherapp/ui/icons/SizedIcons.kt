package com.example.weatherapp.ui.icons

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

/**
 * Customized Icon with default size for consistent icon size across application and ease of management
 * */
@Composable
fun SizedIcon(
    modifier: Modifier = Modifier,
    iconDrawable: Int,
    contentDescription: String,
) {
    Icon(
        modifier = modifier.size (ComposableIconConstants.defaultIconSize),
        painter = painterResource(id = iconDrawable),
        contentDescription = contentDescription,
    )
}