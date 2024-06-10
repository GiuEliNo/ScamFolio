package com.dosti.scamfolio.ui.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dosti.scamfolio.api.model.CoinModelAPI
import kotlinx.coroutines.flow.MutableStateFlow

class SharedCoinGekoViewModel : ViewModel() {
    private val coinLiveData = MutableStateFlow<MutableList<CoinModelAPI>?>(null)

    fun getCoinLiveData() : MutableStateFlow<MutableList<CoinModelAPI>?> {
        return coinLiveData
    }

    fun setCoinLiveData(listCoin:MutableList<CoinModelAPI>){
        coinLiveData.value = listCoin
    }
}