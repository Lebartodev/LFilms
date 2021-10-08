package com.lebartodev.lfilms.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.lebartodev.core.utils.viewBinding
import com.lebartodev.lfilms.BottomNavigationDirections
import com.lebartodev.lfilms.R
import com.lebartodev.lfilms.databinding.FragmentHomePageBinding
import com.lebartodev.lib_navigation.HomeNavigationFlow
import com.lebartodev.lib_navigation.HomeNavigator

class HomePageFragment : Fragment(), HomeNavigator {
    private val binding by viewBinding(FragmentHomePageBinding::inflate)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNavigation.setupWithNavController(getNavController())
    }

    companion object {
        const val TAG = "HomePageFragment"
    }

    private fun getNavController(): NavController {
        return (childFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    override fun navigateTo(direction: NavDirections, extras: Navigator.Extras?) {
        if (extras == null) {
            getNavController().navigate(direction)
        } else {
            getNavController().navigate(direction, extras)
        }
    }

    override fun back() {
        getNavController().popBackStack()
    }

    override fun navigateTo(navigationFlow: HomeNavigationFlow) = when (navigationFlow) {
        HomeNavigationFlow.Trending -> getNavController().navigate(BottomNavigationDirections.actionGlobalTrendingFlow())
        HomeNavigationFlow.Search -> getNavController().navigate(BottomNavigationDirections.actionGlobalSearchFlow())
    }
}