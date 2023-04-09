package com.example.weatherapp.ui.permissions

import android.app.Application
import android.content.Context
import com.example.weatherapp.R
import javax.inject.Inject

/**
 * Provides description for location permission dependent on if the permission has been permanently declined or not
 * */
class LocationPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(context: Context, isPermanentlyDeclined: Boolean): String {
        return if(isPermanentlyDeclined){
            context.getString(R.string.permanently_declined_description)
        }else {
            context.getString(R.string.temp_declined_description)
        }
    }
}