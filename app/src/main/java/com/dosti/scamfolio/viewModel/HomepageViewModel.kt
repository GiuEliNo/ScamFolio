package com.dosti.scamfolio.viewModel

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dosti.scamfolio.SharedPrefRepository
import com.dosti.scamfolio.dbStuff.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomepageViewModel(repository: Repository) : ViewModel(){
    private var _username = ""
    val username=_username
    val repo = repository

    private var balance = MutableStateFlow<Double?>(null)

    fun setUsername(usr: String) {
        _username = usr
    }

    fun setBalance(usr: String, sharedPrefRepository: SharedPrefRepository) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                sharedPrefRepository.putBalance("balance", repo.getBalance(usr))
            }
        }
    }
}