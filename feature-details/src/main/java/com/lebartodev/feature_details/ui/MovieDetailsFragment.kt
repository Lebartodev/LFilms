package com.lebartodev.feature_details.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.request.ImageRequest
import com.lebartodev.core.di.coreComponent
import com.lebartodev.core.fragmentArgs
import com.lebartodev.core.utils.Size
import com.lebartodev.core.utils.viewBinding
import com.lebartodev.feature_details.databinding.FragmentDetailsBinding
import com.lebartodev.feature_details.di.DaggerDetailsComponent
import com.lebartodev.lib_navigation.findNavigator
import com.lebartodev.lib_ui.HorizontalSpaceItemDecoration
import com.lebartodev.lib_ui.dp
import kotlinx.coroutines.launch
import javax.inject.Inject


class MovieDetailsFragment : Fragment() {
    private val binding by viewBinding(FragmentDetailsBinding::inflate)
    private val movieId: Long by fragmentArgs()
    private val castAdapter = CastAdapter {
        findNavigator().navigateTo(MovieDetailsFragmentDirections.actionArtistDetails(it))
    }

    @Inject
    lateinit var factory: DetailsViewModelFactory.Factory
    private val viewModel: DetailsViewModel by viewModels {
        factory.create(movieId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerDetailsComponent.builder()
            .coreComponent(context.coreComponent())
            .build()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.castList.adapter = castAdapter
        binding.castList.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.castList.addItemDecoration(HorizontalSpaceItemDecoration(8.dp))

        binding.home.setOnClickListener { findNavigator().back() }
        viewModel.movie().observe(viewLifecycleOwner) {
            val data = it.data ?: return@observe
            binding.movieDescription.text = data.overview
            binding.title.text = data.title
            binding.ratingView.isVisible = data.voteAverage != null
            binding.ratingView.text = data.voteAverage.toString()
            binding.ratingCount.isVisible = data.voteCount != null
            binding.ratingCount.text = "(${data.voteCount})"
            viewLifecycleOwner.lifecycleScope.launch {
                val provider = context?.coreComponent()?.imageUrlProvider()
                val loader = context?.coreComponent()?.imageLoader()
                val posterPath = data.posterPath
                if (provider != null && loader != null && posterPath != null) {
                    val request = ImageRequest.Builder(requireContext())
                        .data(provider.provideImageUrl(posterPath, Size.BIG))
                        .target(imageView = binding.detailsImage)
                        .build()
                    loader.execute(request)
                }
                binding.shimmerViewContainer.stopShimmer()
                binding.shimmerViewContainer.hideShimmer()
            }
            binding.genresList.text = data.genres.joinToString(" • ") { it.name }
            castAdapter.data = it.data?.cast ?: emptyList()
        }
    }
}