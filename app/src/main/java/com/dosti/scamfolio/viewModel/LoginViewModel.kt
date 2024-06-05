package com.dosti.scamfolio.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dosti.scamfolio.db.entities.User
import com.dosti.scamfolio.dbStuff.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val repository: Repository) : ViewModel() {
    private val _loginResult = MutableStateFlow<User?>(null)

    val loginResult: StateFlow<User?> =_loginResult

    fun checkLogin(
        username: String,
        password: String
    ) {
        viewModelScope.launch {
            val user = withContext(Dispatchers.IO) {
                repository.login(username, password)
            }
            /* if (user != null) {
                changeState()*/
            _loginResult.value = user

        }
    }


    private var _stateLogin = mutableIntStateOf(1)
    var stateLogin = _stateLogin

    fun changeState() {
        if (_stateLogin.intValue == 0) {
            _stateLogin.intValue = 1
            stateLogin = _stateLogin
        } else {
            _stateLogin.intValue = 0
            stateLogin = _stateLogin
        }
    }
}