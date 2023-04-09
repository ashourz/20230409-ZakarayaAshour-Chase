package com.example.weatherapp.ui.permissions

import android.content.Context

/**
 * PermissionTextProvider provides a scalable approach to extending the number of permissions required in the app
 * on providing detailed descriptions for the PermissionDialog
 * */
interface PermissionTextProvider {
    fun getDescription(context: Context, isPermanentlyDeclined: Boolean): String
}