package com.dosti.scamfolio.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dosti.scamfolio.dbStuff.Repository
import com.dosti.scamfolio.model.Coin
import com.dosti.scamfolio.model.api.getCoin
import kotlinx.coroutines.launch

class CryptoScreenViewModel(private val repository: Repository) : ViewModel() {
    private val _coin = MutableLiveData<Coin>()
    val coin: LiveData<Coin> = _coin

    fun fetchCrypto() {
        viewModelScope.launch {
            val newCoin = getCoin()
            _coin.value = newCoin
        }
    }
}