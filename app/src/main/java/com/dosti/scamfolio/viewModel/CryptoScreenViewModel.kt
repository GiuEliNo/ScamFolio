package com.dosti.scamfolio.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dosti.scamfolio.SharedPrefRepository
import com.dosti.scamfolio.dbStuff.Repository
import com.dosti.scamfolio.api.model.CoinModelAPIDB
import com.dosti.scamfolio.api.ConnectionRetrofit
import com.dosti.scamfolio.api.model.CoinModelAPI
import com.dosti.scamfolio.db.entities.Purchasing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Date

class CryptoScreenViewModel(private val repository: Repository, private val sharedCoinGeko: SharedCoinGekoViewModel,
    private val sharedPrefRepository: SharedPrefRepository) : ViewModel() {
    private val _coin = MutableLiveData<CoinModelAPI>()
    val coin: LiveData<CoinModelAPI> = _coin


    private val _username=sharedPrefRepository.getUsr("username", "NULL")

    val username=_username


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
            newCoin.find { it.id == coinId }?.time_fetched = System.currentTimeMillis()
            _coin.value = newCoin[0]

        }
    }

    fun addPurchase(coinName: String, qty: Double, username: String, isNegative: Boolean) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val newPurchasing = Purchasing(0, coinName, qty, username, isNegative)
                repository.insertPurchasing(newPurchasing)
                repository.insertPurchasing(newPurchasing)
            }
        }
    }

    fun getLastUpdate(coin : CoinModelAPI): String? {
        val sdf = SimpleDateFormat("dd/MM/yy hh:mm a")
        val netDate = coin.time_fetched?.let { Date(it) }
        val date = netDate?.let { sdf.format(it) }
        return date
    }
}


