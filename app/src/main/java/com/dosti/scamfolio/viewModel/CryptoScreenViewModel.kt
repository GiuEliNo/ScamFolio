package com.dosti.scamfolio.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dosti.scamfolio.dbStuff.Repository
import com.dosti.scamfolio.api.model.CoinModelAPI
import com.dosti.scamfolio.api.CoinGekoAPI
import kotlinx.coroutines.launch

class CryptoScreenViewModel(private val repository: Repository) : ViewModel() {
    private val _coin = MutableLiveData<CoinModelAPI>()
    val coin: LiveData<CoinModelAPI> = _coin
    fun fetchCrypto(id : String) {
        viewModelScope.launch {
            val newCoin = CoinGekoAPI.coinGekoAPIService.getCoinData(
                "CG-9CHDGjAiUnv7oCnbFEB7KPAN",
                id,
                vsCurrency = "eur",
                order = "market_cap_desc",
                perPage = "250",
                sparklineBoolean = false,
            )
            _coin.value = newCoin
        }
    }
}