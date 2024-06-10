package com.dosti.scamfolio.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.dosti.scamfolio.dbStuff.Repository
import com.dosti.scamfolio.ui.view.SharedCoinGekoViewModel

class ConverterViewModel(private val repository: Repository,private val sharedCoinGeko: SharedCoinGekoViewModel): ViewModel() {




    var firstField = mutableStateOf("")
    var secondField = mutableStateOf("")
    var expanded = mutableStateOf(false)
    val cryptos = listOf("Crypto1", "Crypto2", "Crypto3")
    var choice = mutableStateOf(cryptos[0])


    fun calculate(){}
}