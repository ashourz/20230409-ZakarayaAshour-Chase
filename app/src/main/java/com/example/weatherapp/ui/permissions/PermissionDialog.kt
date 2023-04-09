package com.example.weatherapp.ui.permissions

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.weatherapp.R
import com.example.weatherapp.ui.permissions.LocationPermissionTextProvider
import com.example.weatherapp.ui.permissions.PermissionTextProvider

/**
 * Permission Rationale Dialog
 * */
@Preview
@Composable
fun PermissionDialog(
    modifier: Modifier = Modifier,
    permissionTextProvider: PermissionTextProvider = LocationPermissionTextProvider(),
    isPermanentlyDeclined: Boolean = true,
    onDismiss: () -> Unit = {},
    onOkClick: () -> Unit = {},
    onGoToAppSetttingsClick: () -> Unit = {}
) {
    val localContext = LocalContext.current
    AlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (isPermanentlyDeclined) {
                        onGoToAppSetttingsClick()
                    } else {
                        onOkClick()
                    }
                }
            ) {
                Text(
                    text = if (isPermanentlyDeclined) {
                        stringResource(R.string.grant_permission)
                    } else {
                        stringResource(R.string.ok)
                    }
                )
            }
        },
        title = {
            Text(
                text = stringResource(R.string.permission_required)
            )
        },
        text = {
            Text(
                text = permissionTextProvider.getDescription(localContext, isPermanentlyDeclined)
            )
        }
    )
}

