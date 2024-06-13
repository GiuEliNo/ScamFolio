package com.dosti.scamfolio.viewModel

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dosti.scamfolio.db.entities.User
import com.dosti.scamfolio.dbStuff.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(private val repository: Repository) : ViewModel() {
    private val _loginResult = MutableStateFlow<User?>(null)

    var eventToast= false


    enum class loginScreens{
        LOGIN,REGISTER, HOME
    }



    private var _currentScreen=mutableStateOf(loginScreens.LOGIN)
    var currentScreen= _currentScreen

    fun navigateToLogin(){
        _currentScreen.value=(loginScreens.LOGIN)
    }

    fun navigateToRegister(){
        _currentScreen.value=(loginScreens.REGISTER)
    }

    fun navigateToHome(){
        _currentScreen.value=(loginScreens.HOME)

    }

    val loginResult: StateFlow<User?> =_loginResult

    fun checkLogin(
        username: String,
        password: String
    ) {
        viewModelScope.launch {
            val user = withContext(Dispatchers.IO) {
                repository.login(username, password)
            }
            if (user!=null) {
                _loginResult.value = user
            }
            else {
                eventToast=true
            }
        }
    }


    private var _stateLogin = mutableIntStateOf(1)
    var stateLogin = _stateLogin

    fun createNewUser(
        username: String,
        password: String
    ) {
        viewModelScope.launch {
            val exist = withContext(Dispatchers.IO) {
                repository.checkUserExistence(username)
            }

            if (!exist) {
                withContext(Dispatchers.IO) {
                    repository.signIn(username, password, 0.0)
                }
            } else {
                eventToast = true
            }

        }
    }

    fun resetEventToast(){
        eventToast=false
    }
}