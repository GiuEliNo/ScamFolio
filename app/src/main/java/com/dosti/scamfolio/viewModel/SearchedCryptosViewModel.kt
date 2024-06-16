package com.dosti.scamfolio.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dosti.scamfolio.SharedPrefRepository
import com.dosti.scamfolio.db.entities.CoinModelAPIDB
import com.dosti.scamfolio.db.repositories.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchedCryptosViewModel(
    private val repository: Repository,
    private val sharedCoinGeko: SharedCoinGekoViewModel,
    sharedPrefRepository: SharedPrefRepository
) : ViewModel() {

    private val _username = sharedPrefRepository.getUsr("username", "NULL")
    val username = _username

    val _coinList = sharedCoinGeko.getCoinLiveData()
    var searchQuery by mutableStateOf("")
        private set
    var showWalletOnly by mutableStateOf(false)
        private set

    private val _walletCoins = MutableStateFlow<Map<String, Double>>(emptyMap())
    val walletCoins: StateFlow<Map<String, Double>> = _walletCoins

    init {
        viewModelScope.launch {
            loadWalletCoins()
        }
    }

    val searchResults: StateFlow<List<CoinModelAPIDB>?> =
        snapshotFlow { Pair(searchQuery, showWalletOnly) }
            .combine(_coinList) { (query, showWallet), coins ->
                coins?.filter { coin ->
                    coin.name.contains(query, ignoreCase = true) &&
                            (!showWallet || (walletCoins.value[coin.id] ?: 0.0) > 0)
                }
            }
            .stateIn(
                scope = viewModelScope,
                initialValue = emptyList(),
                started = SharingStarted.WhileSubscribed(5_000)
            )

    private suspend fun loadWalletCoins() {
        val coinNames = repository.getAllCoins(username)
        val coinQuantities = coinNames.associateWith { repository.getQuantityCoinByiD(it, username) }
        _walletCoins.value = coinQuantities
    }

    fun onSearchQueryChange(newQuery: String) {
        searchQuery = newQuery
    }

    fun onToggleShowWallet() {
        showWalletOnly = !showWalletOnly
    }
}