package com.dosti.scamfolio.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dosti.scamfolio.api.model.CoinModelAPI
import com.dosti.scamfolio.dbStuff.Repository
import com.dosti.scamfolio.ui.view.SharedCoinGekoViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchedCryptosViewModel(private val repository: Repository, private val sharedCoinGeko: SharedCoinGekoViewModel) : ViewModel() {
    val _coinList =  sharedCoinGeko.getCoinLiveData()
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
    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery
    }
}