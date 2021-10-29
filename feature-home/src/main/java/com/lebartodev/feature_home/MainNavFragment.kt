package com.lebartodev.feature_home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.lebartodev.lib_utils.utils.AsyncResult
import com.lebartodev.core.utils.viewBinding
import com.lebartodev.feature_home.databinding.FragmentMainNavBinding
import com.lebartodev.feature_splash.SplashFragment
import com.lebartodev.lib_navigation.MainNavigator
import com.lebartodev.lib_trending.TrendingRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainNavFragment : Fragment(), MainNavigator {
    private val binding by viewBinding(FragmentMainNavBinding::inflate)

    @Inject
    private lateinit var trendingRepository: TrendingRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            navigateTo(SplashFragment())
            merge(
                trendingRepository.trending().filter { it is AsyncResult.Success },
                flow {
                    delay(MAX_DELAY)
                    emit(Unit)
                }
            ).first()
            delay(MAX_DELAY)
            navigateTo(HomePageFragment())
        }
    }

    override fun navigateTo(fragment: Fragment) {
        childFragmentManager
            .beginTransaction()
            .replace(R.id.main_nav_host_fragment, fragment, fragment.javaClass.name)
            .addToBackStack(null)
            .commit()
    }

    override fun back() {
        childFragmentManager.popBackStack()
    }

    companion object {
        const val MAX_DELAY = 5000L
        const val TAG = "MainNavFragment"
    }
}