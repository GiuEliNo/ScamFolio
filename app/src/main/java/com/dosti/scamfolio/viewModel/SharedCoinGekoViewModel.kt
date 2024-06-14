package com.dosti.scamfolio.viewModel

import androidx.lifecycle.ViewModel
import com.dosti.scamfolio.db.entities.CoinModelAPIDB
import kotlinx.coroutines.flow.MutableStateFlow

class SharedCoinGekoViewModel : ViewModel() {
    private val coinLiveData = MutableStateFlow<MutableList<CoinModelAPIDB>?>(null)

    fun getCoinLiveData() : MutableStateFlow<MutableList<CoinModelAPIDB>?> {
        return coinLiveData
    }

    fun setCoinLiveData(listCoin:MutableList<CoinModelAPIDB>){
        coinLiveData.value = listCoin
    }
}