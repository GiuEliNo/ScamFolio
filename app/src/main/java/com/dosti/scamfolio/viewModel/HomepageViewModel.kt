package com.dosti.scamfolio.viewModel

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dosti.scamfolio.SharedPrefRepository
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


    //private var _balance = sharedPrefRepository.getBalance("balance", "NULL")
    private var _balance = MutableStateFlow(0.0)
    var balance=_balance

    var transactions=MutableStateFlow<List<Purchasing>>(emptyList())
        init {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    transactions.value=repository.getPurchasingList(username)
                    updateBalanceValue(username)
                }
            }
        }


     fun updateBalanceValue(name: String){
         _balance.value = 0.0
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



   /* fun setBalance(usr: String, sharedPrefRepository: SharedPrefRepository) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                sharedPrefRepository.putBalance("balance", repository.getBalance(usr))
            }
        }
    }

    */
}