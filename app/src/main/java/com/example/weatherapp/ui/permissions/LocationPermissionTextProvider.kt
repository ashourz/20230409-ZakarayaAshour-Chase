package com.example.weatherapp.ui.permissions

/**
 * Provides description for location permission dependent on if the permission has been permanently declined or not
 * */
class LocationPermissionTextProvider: PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if(isPermanentlyDeclined){
            "It seems you permanently declined approximate location permissions. " +
                    "You can go to the app settings to grant it."
        }else "This app requires access to your approximate location to provide " +
                "weather information for you current location."
    }
}