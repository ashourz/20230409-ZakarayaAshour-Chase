package com.example.weatherapp.ui.permissions

/**
 * PermissionTextProvider provides a scalable approach to extending the number of permissions required in the app
 * on providing detailed descriptions for the PermissionDialog
 * */
interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): String
}