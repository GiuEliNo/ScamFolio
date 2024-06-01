package com.dosti.scamfolio.viewModel

import android.app.Application
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dosti.scamfolio.db.repositories.UserRepository
import com.dosti.scamfolio.dbStuff.Repository
import com.dosti.scamfolio.dbStuff.User
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository) : ViewModel() {
    fun checkLogin(username : String, password : String) {
        viewModelScope.launch {
            val user = repository.login(username, password)
            if (user != null) {
                changeState()
            }
        }
    }

    private var _stateLogin= mutableIntStateOf(1)
    var stateLogin= _stateLogin

    fun changeState(){
        _stateLogin.intValue=0
        stateLogin=_stateLogin
    }
}