package com.dosti.scamfolio.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.dosti.scamfolio.db.repositories.UserRepository

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val repository : UserRepository
}