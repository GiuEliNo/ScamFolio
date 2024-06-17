package com.dosti.scamfolio.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dosti.scamfolio.api.initializeDataAPI
import com.dosti.scamfolio.db.repositories.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashScreenViewModel(private val repository: Repository, private val sharedCoinGeko: SharedCoinGekoViewModel) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    suspend fun initDb(): Boolean {
        val successAsync = CoroutineScope(Dispatchers.Default).async {
            var success = true
            if (repository.isEmpty()) {
                if(initializeDataAPI(repository, false))
                    Log.d("Database", "Database was empty. Initialized")
                else
                    success = false
            }

            if (System.currentTimeMillis() - repository.chechFetchedDate() > 6000000) {
                repository.resetCoinList()
                if(initializeDataAPI(repository, false))
                    Log.d("Database", "Database entries were older than a week. Updated")
                else
                    success = false
            }

            if(initializeDataAPI(repository, true))
                Log.d("Database", "Prices updated")
            else
                success = false

            Log.d("Database", "Database all right")
            fetchAllCryptos()
            _isLoading.value = false
            return@async success
        }

        val success = successAsync.await()
        return success
    }

    private fun fetchAllCryptos(){

        viewModelScope.launch(Dispatchers.IO) {
            val newListCoin = repository.loadAllCoin()
            sharedCoinGeko.setCoinLiveData(newListCoin)
        }
    }
}