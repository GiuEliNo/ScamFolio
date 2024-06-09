package com.dosti.scamfolio.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dosti.scamfolio.api.model.CoinModelAPI
import com.dosti.scamfolio.dbStuff.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchedCryptosViewModel(private val repository: Repository) : ViewModel() {
    val _coinList =  MutableStateFlow<MutableList<CoinModelAPI>?>(null)
    val coinList: StateFlow<MutableList<CoinModelAPI>?>
        get() = _coinList
    var searchQuery by mutableStateOf("")
        private set

    val searchResults: StateFlow<List<CoinModelAPI>?> =
        snapshotFlow { searchQuery }
            .combine(_coinList) { searchQuery, coins ->
                when {
                    searchQuery.isNotEmpty() -> coins?.filter { coins ->
                        coins.name.contains(searchQuery, ignoreCase = true)
                    }
                    else -> coins
                }
            }.stateIn(
                scope = viewModelScope,
                initialValue = emptyList(),
                started = SharingStarted.WhileSubscribed(5_000)
            )


    fun fetchAllCryptos() {

        viewModelScope.launch {
           val newListCoin = repository.loadAllCoin()
           _coinList.value = newListCoin
        }
    }



    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery
    }
}