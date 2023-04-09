package com.example.weatherapp.ui

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.extensions.getActivity
import com.example.weatherapp.extensions.openAppSettings
import com.example.weatherapp.presentation.DataViewModel
import com.example.weatherapp.ui.permissions.LocationPermissionTextProvider
import com.example.weatherapp.ui.permissions.PermissionDialog

/**
 * Provides Dialog and Permission Request manager for scaffold with methods that require user granted permissions
 * */
@Composable
fun PermissionRequestScaffoldWrapper(
    viewModel: DataViewModel = viewModel(),
    permissionedScaffold: @Composable (multiplePermissionResultLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>) -> Unit
) {
    val localContext = LocalContext.current
    val activity = localContext.getActivity()
    val dialogQueue = viewModel.visiblePermissionDialogQueue

    //Activity Result Launcher State for Request Permission Dialog Launcher
    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissionGrantedMap ->
            permissionGrantedMap.forEach { (permissionString, isGranted) ->
                viewModel.onPermissionResult(
                    permissionString,
                    isGranted = isGranted
                )
            }
        })

    //Scaffold with actions requiring permissions
    permissionedScaffold(multiplePermissionResultLauncher)

    //Displays Permission Rationale Dialog on Decline of Permission
    if (activity != null) {
        dialogQueue.forEach { permission ->
            PermissionDialog(
                permissionTextProvider = when (permission) {
                    Manifest.permission.ACCESS_COARSE_LOCATION -> LocationPermissionTextProvider()
                    else -> return@forEach
                },
                isPermanentlyDeclined = (
                        ActivityCompat.checkSelfPermission(activity.applicationContext, permission) == PackageManager.PERMISSION_DENIED &&
                                !ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)),
                onDismiss = viewModel::dismissPermissionDialogQueue,
                onOkClick = {
                    viewModel.dismissPermissionDialogQueue()
                    multiplePermissionResultLauncher.launch(arrayOf(permission))
                },
                onGoToAppSetttingsClick = {
                    viewModel.dismissPermissionDialogQueue()
                    activity.openAppSettings()
                }
            )
        }
    }
}