package com.dosti.scamfolio.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.extensions.roundTwoDecimal
import com.dosti.scamfolio.SharedPrefRepository
import com.dosti.scamfolio.api.model.Wallet
import com.dosti.scamfolio.db.entities.Purchasing
import com.dosti.scamfolio.dbStuff.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomepageViewModel(private val repository: Repository,
                        sharedPrefRepository: SharedPrefRepository) : ViewModel(){
    private var _username = sharedPrefRepository.getUsr("username", "NULL")
    var username=_username
    val myWallet=MutableStateFlow<List<Wallet>>(emptyList())


    private var _balance = MutableStateFlow(0.0)
    var balance=_balance

    var transactions=MutableStateFlow<List<Purchasing>>(emptyList())
        init {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    transactions.value=repository.getPurchasingList(username)
                    updateBalanceValue(username)
                    getWalletInfo(username)
                }
            }
        }

    fun getWalletInfo(name: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                myWallet.value = repository.getAllCoinSummary(username)
            }
        }
    }




     private fun updateBalanceValue(name: String){
         viewModelScope.launch {
             withContext(Dispatchers.IO) {
                 val myBalance = repository.getAllPurchasingForBalance(username)
                 myBalance.forEach{index ->
                     if (!index.isNegative ) {
                         _balance.value += index.quantity * index.price
                         Log.e("Balance update", "finalBalance := $_balance.value\nindex.quantity := ${index.quantity}\n index.price := ${index.price}")
                     }
                     else{
                         _balance.value -= index.quantity * index.price
                         Log.e("Balance update", "finalBalance := $_balance.value\nindex.quantity := ${index.quantity}\n index.price := ${index.price}")
                     }
                 }
             }
         }
         Log.e("Balance update", "finalBalance ritornato := $_balance.value")
    }

    fun roundDouble(double:Double):Double{
        return double.roundTwoDecimal()
    }



   /* fun setBalance(usr: String, sharedPrefRepository: SharedPrefRepository) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                sharedPrefRepository.putBalance("balance", repository.getBalance(usr))
            }
        }
    }

    */




}