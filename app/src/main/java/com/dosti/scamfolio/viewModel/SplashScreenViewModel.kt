package com.dosti.scamfolio.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dosti.scamfolio.api.initializeDataAPI
import com.dosti.scamfolio.dbStuff.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashScreenViewModel(private val repository: Repository, private val sharedCoinGeko: SharedCoinGekoViewModel) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            if (repository.isEmpty()) {
                initializeDataAPI(repository)
                Log.d("Database", "Database was empty. Initialized")
            }

            if (System.currentTimeMillis() - repository.chechFetchedDate() > 600000) {
                //if one week old
                repository.resetCoinList()
                initializeDataAPI(repository)
                Log.d("Database", "Database entries were older than a week. Updated")
            }

            Log.d("Database", "Database all right")
            fetchAllCryptos()
            _isLoading.value = false

            }
    }
    private fun fetchAllCryptos(){

        viewModelScope.launch(Dispatchers.IO) {
            val newListCoin = repository.loadAllCoin()
            sharedCoinGeko.setCoinLiveData(newListCoin)
        }
    }
}