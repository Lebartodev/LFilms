package com.lebartodev.feature_login.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lebartodev.lib_authentication.repository.AccountManager
import kotlinx.coroutines.flow.onEach
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
            manager.account().onEach { setState { copy(complete = true) } }
            manager.requestToken().onEach { setState { copy(requestToken = it, loading = false) } }
        }
    }

    fun checkAccount() {
        setState { copy(loading = true) }
        manager.updateSessionId()
    }

    private fun setState(reducer: LoginUiState.() -> LoginUiState) {
        loginUiState = reducer.invoke(loginUiState)
    }
}