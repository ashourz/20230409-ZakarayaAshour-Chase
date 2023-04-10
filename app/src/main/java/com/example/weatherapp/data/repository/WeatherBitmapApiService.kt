package com.example.weatherapp.data.repository

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap
import com.example.weatherapp.cache.ImagesCache
import com.example.weatherapp.constants.ApiBaseUrl
import java.io.InputStream
import java.net.URL

class WeatherBitmapApiService(
    private val imagesCache: ImagesCache
) : BitmapApiServiceBaseClass() {

    /**
     * Url request to retrieve weather icon bitmap
     * */
    override fun requestBitmap(imageName: String): Bitmap? {
        return try {
            val baseUrl = ApiBaseUrl.WEATHER_BITMAP_BASE_URL.baseUrl
            val tailUrl = "@2x.png"
            val inputStream: InputStream = URL(baseUrl.plus(imageName).plus(tailUrl)).content as InputStream
            val imageBitmap = Drawable.createFromStream(inputStream, "src name")?.toBitmap(200, 200, Bitmap.Config.ARGB_8888)
            imageBitmap?.setHasAlpha(true)
            imagesCache.addImageToCache(imageName, imageBitmap)
            imageBitmap
        } catch (e: Exception) {
            //ON EXCEPTION RETURN NULL. FURTHER DEVELOPMENT COULD RETRY THESE REQUESTS
            null
        }
    }
}

open class BitmapApiServiceBaseClass() {

    /**
     * Url request to retrieve weather icon bitmap
     * */
    open fun requestBitmap(imageName: String): Bitmap? {
        return null
    }
}

class FakeBitmapApiService(
    private val imagesCache: ImagesCache
) : BitmapApiServiceBaseClass() {
    /**
     * Url request to retrieve weather icon bitmap
     * */
    override fun requestBitmap(imageName: String): Bitmap? {
        return null
    }
}
