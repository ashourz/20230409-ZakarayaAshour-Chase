package com.example.weatherapp.cache;

import android.graphics.Bitmap;

import androidx.collection.LruCache;

import javax.annotation.Nullable;

/**
 * Creates Image cache 1/8 size of max memory.
 * This is used as to cache bitmaps for the weather icons to be displayed.
 * */
public class ImagesCache {
    public ImagesCache(){}
    private  LruCache<String, Bitmap> imagesCache;

    private static ImagesCache cache;

    public static ImagesCache getInstance()
    {
        if(cache == null)
        {
            cache = new ImagesCache();
        }

        return cache;
    }

    public void initializeCache()
    {
        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() /1024);
        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        System.out.println("cache size = "+cacheSize);

        imagesCache = new LruCache<String, Bitmap>(cacheSize)
        {
            protected int sizeOf(String key, Bitmap value)
            {
                // The cache size will be measured in kilobytes rather than number of items.

                int bitmapByteCount = value.getRowBytes() * value.getHeight();

                return bitmapByteCount / 1024;
            }
        };
    }

    public void addImageToCache(String key, Bitmap value)
    {
        if(imagesCache != null && imagesCache.get(key) == null)
        {
            imagesCache.put(key, value);
        }
    }

    @Nullable
    public Bitmap getImageFromCache(String key)
    {
        if(key != null)
        {
            return imagesCache.get(key);
        }
        else
        {
            return null;
        }
    }

    public void removeImageFromWarehouse(String key)
    {
        imagesCache.remove(key);
    }

    public void clearCache()
    {
        if(imagesCache != null)
        {
            imagesCache.evictAll();
        }
    }

}