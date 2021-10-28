package com.lebartodev.feature_login.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.lebartodev.core.di.coreComponent
import com.lebartodev.core.utils.ViewModelFactory
import com.lebartodev.feature_login.R
import com.lebartodev.feature_login.di.DaggerLoginComponent
import javax.inject.Inject

class LoginFragment : Fragment() {
    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: LoginViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerLoginComponent.builder()
            .coreComponent(context.coreComponent())
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(context = requireContext()).apply {
            setContent {
                LoginContent(viewModel = viewModel)
            }
        }
    }
}

@Composable
private fun LoginContent(viewModel: LoginViewModel) {
    val loginUiState = viewModel.loginUiState
    val context = LocalContext.current
    LaunchedEffect(loginUiState.complete) {
        if (loginUiState.complete)
            Toast.makeText(context, "DONE", Toast.LENGTH_SHORT).show()
    }
    OnLifecycleEvent { _, event ->
        if (event == Lifecycle.Event.ON_RESUME) {
            viewModel.checkAccount()
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextButton(onClick = {
                val browserIntent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.themoviedb.org/authenticate/${loginUiState.requestToken}")
                )
                context.startActivity(browserIntent)
            }) {
                Text(
                    text = "Login",
                    color = colorResource(id = R.color.black),
                    modifier = Modifier
                        .background(
                            colorResource(id = R.color.orange_peel),
                            RoundedCornerShape(10)
                        )
                        .padding(8.dp)
                )
            }
            if (loginUiState.loading) {
                CircularProgressIndicator(
                    color = colorResource(id = R.color.orange_peel),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
private fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}

