package com.example.weatherapp.ui.screens

import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.example.weatherapp.ui.icons.SizedIcon
import com.example.weatherapp.ui.navigation.NavDestinationEnum

/**
 * Weather Scaffold Bottom Bar (in portrait)
 * */
@Composable
fun PortraitBottomNavigationBar(
    selectedDestination: NavDestinationEnum,
    onNavigationItemClick: (NavDestinationEnum) -> Unit
) {
    BottomNavigation() {
        NavDestinationEnum.values().forEach { it ->
            BottomNavigationItem(
                modifier = Modifier.weight(1f),
                icon = {
                    SizedIcon(
                        iconDrawable = it.icon,
                        contentDescription = it.name
                    )
                },
                selected = it == selectedDestination,
                onClick = { onNavigationItemClick(it) }
            )
        }
    }
}

/**
 * Weather Scaffold Bottom Bar (in landscape)
 * */
@Composable
fun LandscapeSideNavigationBar(
    selectedDestination: NavDestinationEnum,
    onNavigationItemClick: (NavDestinationEnum) -> Unit
) {
    NavigationRail(
        backgroundColor = MaterialTheme.colors.primarySurface,
        contentColor = contentColorFor(backgroundColor),
        elevation = BottomNavigationDefaults.Elevation,
    ) {
        NavDestinationEnum.values().reversed().forEach { it ->
            NavigationRailItem(
                modifier = Modifier.weight(1f).semantics { contentDescription = it.name },
                icon = {
                    SizedIcon(
                        iconDrawable = it.icon,
                        contentDescription = it.name
                    )
                },
                selected = it == selectedDestination,
                onClick = { onNavigationItemClick(it) },
                selectedContentColor = MaterialTheme.colors.onSurface,
                unselectedContentColor = MaterialTheme.colors.onSurface.copy(alpha = ContentAlpha.medium)
            )
        }
    }
}