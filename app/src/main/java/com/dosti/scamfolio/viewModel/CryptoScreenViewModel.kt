package com.dosti.scamfolio.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dosti.scamfolio.dbStuff.Repository
import com.dosti.scamfolio.api.model.CoinModelAPIDB
import com.dosti.scamfolio.api.ConnectionRetrofit
import com.dosti.scamfolio.api.model.CoinModelAPI
import com.dosti.scamfolio.db.entities.Purchasing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CryptoScreenViewModel(private val repository: Repository, private val sharedCoinGeko: SharedCoinGekoViewModel) : ViewModel() {
    private val _coin = MutableLiveData<CoinModelAPI>()
    val coin: LiveData<CoinModelAPI> = _coin
    fun fetchCrypto(coinId : String) {
        viewModelScope.launch {
            val newCoin = ConnectionRetrofit.callApi().getCoinData(
                "CG-9CHDGjAiUnv7oCnbFEB7KPAN",
                coinId.lowercase(),
                vsCurrency = "eur",
                order = "market_cap_desc",
                perPage = "250",
                sparklineBoolean = true,
            )
            _coin.value = newCoin[0]

        }
    }

    fun addPurchase(coinName: String, qty: Double, username: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val newPurchasing = Purchasing(0, coinName, qty, username)
                repository.insertPurchasing(newPurchasing)
            }
        }
    }

    fun addSelling(coinName: String, qty: Double, username: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val newPurchasing = Purchasing(0, coinName, qty, username)
                repository.insertPurchasing(newPurchasing)
            }
        }
    }

    fun getPurchase() {}
}