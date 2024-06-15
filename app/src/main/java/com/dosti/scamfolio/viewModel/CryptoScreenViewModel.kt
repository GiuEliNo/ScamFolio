package com.dosti.scamfolio.viewModel

import android.annotation.SuppressLint
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dosti.scamfolio.SharedPrefRepository
import com.dosti.scamfolio.dbStuff.Repository
import com.dosti.scamfolio.api.ConnectionRetrofit
import com.dosti.scamfolio.api.model.CoinModelAPI
import com.dosti.scamfolio.db.entities.Purchasing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date

class CryptoScreenViewModel(private val repository: Repository, private val sharedCoinGeko: SharedCoinGekoViewModel,
    private val sharedPrefRepository: SharedPrefRepository) : ViewModel() {
    private val _coin = MutableLiveData<CoinModelAPI>()
    val coin: LiveData<CoinModelAPI> = _coin

    private val _walletCoin = MutableLiveData<String>()
    val walletCoin: LiveData<String> get() = _walletCoin


    private val _username = sharedPrefRepository.getUsr("username", "NULL")

    val username = _username

    private val _value: MutableStateFlow<String> = MutableStateFlow("")

    val value: StateFlow<String>
        get() = _value


    fun fetchCrypto(coinId: String) {
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

    private suspend fun insertPurchaseWithRetry(
        purchasing: Purchasing,
        maxRetries: Int,
        delayMillis: Long
    ) {

        repeat(maxRetries) { attempt ->
            try {
                repository.insertPurchasing(purchasing)
                Log.e("Inserimento Purchase", "Inserimento avvenuto con successo")
                return
            } catch (e: SQLiteConstraintException) {
                Log.e(
                    "Inserimento Purchase",
                    "Errore nell'inserimento, riprovo tra $delayMillis ms",
                    e
                )
                delay(delayMillis)
            }
        }

    }

    fun addPurchase(coinName: String, username: String, isNegative: Boolean) {

        val qty = runBlocking { value.first().toDoubleOrNull() ?: 0.0 }

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val newPurchasing = Purchasing(0, coinName, qty, username, isNegative)
                    insertPurchaseWithRetry(newPurchasing, 5, 1000)

                } catch (e: Exception) {
                    Log.e("Purchasing", "Errore durante l'inserimento dell'acquisto", e)
                }
            }
            setValue("0")
        }

    }

    @SuppressLint("SimpleDateFormat")
    fun getLastUpdate(coin: CoinModelAPI): String? {
        val sdf = SimpleDateFormat("dd/MM/yy hh:mm a")
        val netDate = coin.time_fetched?.let { Date(it) }
        val date = netDate?.let { sdf.format(it) }
        return date
    }

    fun getWalletCoin(coinId: String) {
        val username = sharedPrefRepository.getUsr("username", "NULL")
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                repository.getQuantityCoinByiD(coinId, username)
            }
            _walletCoin.value = result
        }
    }

    fun checkIfCanRemove(coinName: String): Boolean {
        getWalletCoin(coinName)
        val actualCoin = walletCoin.value?.toDoubleOrNull() ?: 0.0
        val inTextValue = runBlocking { value.first().toDoubleOrNull() ?: 0.0 }

        return actualCoin - inTextValue >= 0.0
    }

    fun setValue(it: String){
        _value.value = it
    }
}


