package com.dosti.scamfolio.viewModel

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dosti.scamfolio.R
import com.dosti.scamfolio.api.initializeDataAPI
import com.dosti.scamfolio.dbStuff.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.SelectClause1
import kotlinx.coroutines.withContext

class SplashScreenViewModel(private val repository: Repository, private val sharedCoinGeko: SharedCoinGekoViewModel) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _toastMessage = MutableLiveData<String?>(null)
    //val toastMessage: LiveData<String?> = _toastMessage

   //@OptIn(ExperimentalCoroutinesApi::class)
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
                //if one week old
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

    /* fun clearErrorMessage() {
        _toastMessage.value = null
    }*/
}