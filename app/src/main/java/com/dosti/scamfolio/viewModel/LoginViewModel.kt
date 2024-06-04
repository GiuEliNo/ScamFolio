package com.dosti.scamfolio.viewModel

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dosti.scamfolio.db.entities.User
import com.dosti.scamfolio.dbStuff.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository) : ViewModel() {
    fun checkLogin(username : String, password : String) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = repository.login(username, password)
            if (user != null) {
                changeState()
            }
        }
    }

    private var _stateLogin= mutableIntStateOf(1)
    var stateLogin= _stateLogin

    fun changeState(){
        if (_stateLogin.intValue == 0) {
            _stateLogin.intValue = 1
            stateLogin = _stateLogin
        } else {
            _stateLogin.intValue = 0
            stateLogin = _stateLogin
        }
    }
}