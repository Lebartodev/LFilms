package com.lebartodev.feature_login.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lebartodev.lib_authentication.manager.AccountManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val requestToken: String? = null,
    val loading: Boolean = true,
    val complete: Boolean = false
)

class LoginViewModel @Inject constructor(
    private val manager: AccountManager
) : ViewModel() {
    var loginUiState by mutableStateOf(LoginUiState())
        private set

    init {
        viewModelScope.launch {
            manager.requestToken()
                .collect { setState { copy(requestToken = it, loading = false) } }
        }
        viewModelScope.launch {
            manager.account()
                .collect { setState { copy(complete = true) } }
        }
    }

    fun checkAccount() {
        //setState { copy(loading = true) }
        manager.updateSessionId()
    }

    private fun setState(reducer: LoginUiState.() -> LoginUiState) {
        loginUiState = reducer.invoke(loginUiState)
    }
}