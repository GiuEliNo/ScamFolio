package com.dosti.scamfolio.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dosti.scamfolio.dbStuff.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateAccountViewModel(private val repository: Repository): ViewModel() {
    fun createNewUser(
        username: String,
        password: String
    ){
        CoroutineScope(Dispatchers.IO).launch {
            repository.signIn(username, password, 0.0)
        }
    }
}