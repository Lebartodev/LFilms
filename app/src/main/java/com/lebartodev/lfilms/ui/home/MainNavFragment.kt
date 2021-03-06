package com.lebartodev.lfilms.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.lebartodev.core.utils.viewBinding
import com.lebartodev.feature_login.ui.LoginFragment
import com.lebartodev.feature_splash.SplashFragment
import com.lebartodev.lfilms.R
import com.lebartodev.lfilms.databinding.FragmentMainNavBinding
import com.lebartodev.lib_navigation.MainNavigator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainNavFragment : Fragment(), MainNavigator {
    private val binding by viewBinding(FragmentMainNavBinding::inflate)

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