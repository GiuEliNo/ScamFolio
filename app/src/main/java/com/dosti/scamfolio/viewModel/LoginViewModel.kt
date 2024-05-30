package com.dosti.scamfolio.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.dosti.scamfolio.db.repositories.UserRepository
import com.dosti.scamfolio.dbStuff.Repository
import com.dosti.scamfolio.dbStuff.User

class LoginViewModel(private val repository: Repository) : ViewModel() {
    fun checkLogin(username : String, password : String): String {
        val user : User = repository.login(username, password)
        if (user != null)
            return "ok"
        else
            return "wrong creds"
    }
}