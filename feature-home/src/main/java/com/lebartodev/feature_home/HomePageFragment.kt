package com.lebartodev.feature_home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lebartodev.core.utils.viewBinding
import com.lebartodev.feature_home.databinding.FragmentHomePageBinding
import com.lebartodev.feature_login.ui.LoginFragment
import com.lebartodev.feature_search.ui.SearchFragment
import com.lebartodev.feature_trending.ui.TrendingFragment

class HomePageFragment : Fragment() {
    private val binding by viewBinding(FragmentHomePageBinding::inflate)
    private val trendingFragment by lazy { TrendingFragment() }
    private val accountFragment by lazy { LoginFragment() }
    private val searchFragment by lazy { SearchFragment() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNavigation.setOnItemSelectedListener {
            val fragment = when (it.itemId) {
                R.id.trending_flow -> trendingFragment
                R.id.search_flow -> searchFragment
                R.id.account_flow -> accountFragment
                else -> null
            }
            if (fragment != null) {
                childFragmentManager
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment, fragment.javaClass.name)
                    .commit()
                return@setOnItemSelectedListener true
            }
            false
        }
        binding.bottomNavigation.selectedItemId = R.id.trending_flow
    }

    companion object {
        const val TAG = "HomePageFragment"
    }
}