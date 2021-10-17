package com.lebartodev.feature_details.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.lebartodev.core.di.coreComponent
import com.lebartodev.core.utils.Size
import com.lebartodev.feature_details.databinding.ICastBinding
import com.lebartodev.lib.data.entity.CastEntity

class CastAdapter : RecyclerView.Adapter<CastAdapter.ViewHolder>() {
    var data: List<CastEntity> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return data[position].id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ICastBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }


    class ViewHolder(private val binding: ICastBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: CastEntity) {
            binding.artistName.text = data.name
            binding.artistRole.text = data.character
            val path = data.profilePath ?: return
            val loader = binding.root.context.coreComponent().imageLoader()
            val provider = binding.root.context.coreComponent().imageUrlProvider()
            val request = ImageRequest.Builder(binding.root.context)
                .data(provider.provideImageUrl(path, Size.SMALL))
                .target(imageView = binding.artistImage)
                .transformations(CircleCropTransformation())
                .build()
            loader.enqueue(request)
        }
    }

    override fun getItemCount(): Int = data.size
}