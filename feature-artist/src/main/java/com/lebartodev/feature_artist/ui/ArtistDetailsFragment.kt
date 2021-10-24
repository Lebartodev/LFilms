package com.lebartodev.feature_artist.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import coil.request.ImageRequest
import com.lebartodev.core.di.coreComponent
import com.lebartodev.core.fragmentArgs
import com.lebartodev.core.utils.Size
import com.lebartodev.core.utils.viewBinding
import com.lebartodev.feature_artist.databinding.FragmentArtistDetailsBinding
import com.lebartodev.feature_artist.di.DaggerArtistComponent
import com.lebartodev.lib_navigation.navigator
import kotlinx.coroutines.launch
import javax.inject.Inject


class ArtistDetailsFragment : Fragment() {
    private val binding by viewBinding(FragmentArtistDetailsBinding::inflate)
    private val creditId: String by fragmentArgs()

    @Inject
    lateinit var factory: ArtistViewModelFactory.Factory
    private val viewModel: ArtistViewModel by viewModels { factory.create(creditId) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerArtistComponent.builder()
            .coreComponent(context.coreComponent())
            .build()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButton.setOnClickListener { navigator.back() }
        viewModel.artist().observe(viewLifecycleOwner) {
            val data = it.data ?: return@observe
            binding.artistBiography.text = data.biography
            binding.artistName.text = data.name
            binding.artistBirthPlace.text = data.placeOfBirth

            viewLifecycleOwner.lifecycleScope.launch {
                val provider = context?.coreComponent()?.imageUrlProvider()
                val loader = context?.coreComponent()?.imageLoader()
                val posterPath = data.profilePath
                if (provider != null && loader != null && posterPath != null) {
                    val request = ImageRequest.Builder(requireContext())
                        .data(provider.provideImageUrl(posterPath, Size.BIG))
                        .target(imageView = binding.avatarImage)
                        .build()
                    loader.execute(request)
                }
                binding.shimmerViewContainer.stopShimmer()
                binding.shimmerViewContainer.hideShimmer()
            }
            binding.artistBirth.text = listOf(data.birthday, data.deathday)
                .joinToString(" - ") { it.toString() }
        }
    }

    companion object {
        fun create(creditId: String): ArtistDetailsFragment {
            val fragment = ArtistDetailsFragment()
            val args = Bundle()
            args.putString("creditId", creditId)
            fragment.arguments = args
            return fragment
        }
    }
}