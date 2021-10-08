package com.lebartodev.core.utils

interface ImageUrlProvider {
    fun provideImageUrl(imagePath: String, size: Size = Size.SMALL): String
}

enum class Size { BIG, SMALL }