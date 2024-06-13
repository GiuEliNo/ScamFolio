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
import com.dosti.scamfolio.db.entities.Purchasing
import com.dosti.scamfolio.dbStuff.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomepageViewModel(private val repository: Repository,
                        sharedPrefRepository: SharedPrefRepository) : ViewModel(){
    private var _username = sharedPrefRepository.getUsr("username", "NULL")
    var username=_username
    val repo = repository

    private var _balance = sharedPrefRepository.getBalance("balance", "NULL")
    var balance=_balance

    var transactions=MutableStateFlow<List<Purchasing>>(emptyList())
        init {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    transactions.value=repo.getPurchasingList(username) }
            }
        }
        //listOf(Purchasing(0, "Bitcoin", 10000.0, "a", true), Purchasing(0, "Bitcoin", 15.0, "a", false))

    /*
    fun setUsername(usr: String) {
        _username = usr
    } */

    fun setBalance(usr: String, sharedPrefRepository: SharedPrefRepository) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                sharedPrefRepository.putBalance("balance", repo.getBalance(usr))
            }
        }
    }
}