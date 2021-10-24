package com.lebartodev.feature_trending.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lebartodev.core.di.coreComponent
import com.lebartodev.core.utils.ViewModelFactory
import com.lebartodev.core.utils.viewBinding
import com.lebartodev.feature_details.ui.MovieDetailsFragment
import com.lebartodev.feature_trending.databinding.FragmentTrendingBinding
import com.lebartodev.feature_trending.di.DaggerTrendingComponent
import com.lebartodev.lib_navigation.navigator
import javax.inject.Inject

class TrendingFragment : Fragment() {
    private val binding by viewBinding(FragmentTrendingBinding::inflate)

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: TrendingViewModel by viewModels { factory }
    private val trendingAdapter = TrendingAdapter { movieId ->
        navigator.navigateTo(MovieDetailsFragment.create(movieId))
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
        DaggerTrendingComponent.builder()
            .coreComponent(context.coreComponent())
            .build()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.trendingList.adapter = trendingAdapter
        binding.trendingList.layoutManager = LinearLayoutManager(context)
        viewModel.trending().observe(viewLifecycleOwner) {
            (binding.trendingList.adapter as TrendingAdapter).data = it
        }
        viewModel.error().observe(viewLifecycleOwner) {
            it?.run { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
        }
        viewModel.loading().observe(viewLifecycleOwner) {
            binding.refreshTrendingLayout.isRefreshing = it
        }
        binding.refreshTrendingLayout.setOnRefreshListener {
            viewModel.refreshTrending()
        }
    }
}