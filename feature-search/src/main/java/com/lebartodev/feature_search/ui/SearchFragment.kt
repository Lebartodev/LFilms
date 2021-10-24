package com.lebartodev.feature_search.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.lebartodev.core.di.coreComponent
import com.lebartodev.core.utils.ViewModelFactory
import com.lebartodev.core.utils.viewBinding
import com.lebartodev.feature_details.ui.MovieDetailsFragment
import com.lebartodev.feature_search.databinding.FragmentSearchBinding
import com.lebartodev.feature_search.di.DaggerSearchComponent
import com.lebartodev.lib_navigation.navigator
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class SearchFragment : Fragment() {
    private val binding by viewBinding(FragmentSearchBinding::inflate)

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: SearchViewModel by viewModels { factory }
    private val adapter = SearchAdapter { movieId ->
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
        DaggerSearchComponent.builder()
            .coreComponent(context.coreComponent())
            .build()
            .inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.resultList.adapter = adapter
        binding.resultList.layoutManager = GridLayoutManager(context, SEARCH_GRID_SPAN_COUNT)
        viewModel.error().observe(viewLifecycleOwner) {
            it?.run { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
        }
        binding.searchText.doAfterTextChanged {
            viewModel.search(it?.toString() ?: "")
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.result.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    companion object {
        const val SEARCH_GRID_SPAN_COUNT = 3
    }
}