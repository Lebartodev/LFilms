package com.lebartodev.lib_ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.setPadding
import coil.request.ImageRequest
import com.lebartodev.core.di.coreComponent
import com.lebartodev.core.utils.Size
import com.lebartodev.lib.data.entity.MovieEntity
import com.lebartodev.lib_ui.databinding.IMovieBinding

class MovieView(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {
    private val binding = IMovieBinding.inflate(LayoutInflater.from(context), this)

    init {
        binding.root.setPadding(8.dp)
    }

    fun setMovie(movieEntity: MovieEntity) {
        binding.title.text = movieEntity.title
        loadImage(movieEntity.posterPath)
    }

    private fun loadImage(imagePath: String?) {
        imagePath ?: return
        val loader = context.coreComponent().imageLoader()
        val provider = context.coreComponent().imageUrlProvider()
        val request = ImageRequest.Builder(context)
            .data(provider.provideImageUrl(imagePath, Size.SMALL))
            .target(imageView = binding.poster)
            .build()
        loader.enqueue(request)
    }

}