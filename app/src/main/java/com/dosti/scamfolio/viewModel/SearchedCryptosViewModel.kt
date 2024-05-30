package com.dosti.scamfolio.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dosti.scamfolio.api.CoinGekoAPI
import com.dosti.scamfolio.api.model.CoinModelAPI
import com.dosti.scamfolio.dbStuff.Repository
import kotlinx.coroutines.launch

class SearchedCryptosViewModel(private val repository: Repository) : ViewModel() {
    private val _coinList =  MutableLiveData<MutableList<CoinModelAPI>>()
    val coinList : MutableLiveData<MutableList<CoinModelAPI>> = _coinList
    fun fetchAllCryptos() {
        viewModelScope.launch {
            val newListCoin = CoinGekoAPI.coinGekoAPIService.getAllCoins(
                "CG-9CHDGjAiUnv7oCnbFEB7KPAN",
                vsCurrency = "eur",
                order = "market_cap_desc",
                perPage = "250",
                sparklineBoolean = false,
            )
            _coinList.value = newListCoin
        }
    }
}