package com.lebartodev.core.utils

import com.lebartodev.core.BuildConfig
import javax.inject.Inject

class ImageUrlProviderImpl @Inject constructor() : ImageUrlProvider {
    private val baseUrl: String = BuildConfig.IMAGES_PATH

    override fun provideImageUrl(imagePath: String, size: Size): String {
        return baseUrl + (if (size == Size.BIG) "w500" else "w200") + imagePath
    }

}