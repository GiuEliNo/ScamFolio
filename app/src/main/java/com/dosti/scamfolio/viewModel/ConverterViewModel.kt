package com.dosti.scamfolio.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dosti.scamfolio.api.model.CoinModelAPIDB
import com.dosti.scamfolio.dbStuff.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ConverterViewModel(private val repository: Repository,private val sharedCoinGeko: SharedCoinGekoViewModel): ViewModel() {


    private val _coinList= MutableStateFlow<List<CoinModelAPIDB>?>(emptyList())
    var coinList: StateFlow<List<CoinModelAPIDB>?> = _coinList
    var firstField = mutableStateOf("")
    var secondField = mutableStateOf("")
    var choice1 = mutableStateOf("")

    var choice2 = mutableStateOf("")


    init{
        viewModelScope.launch{
            sharedCoinGeko.getCoinLiveData().collect{
                coins -> _coinList.value = coins
                if(coins!!.isNotEmpty()){
                    choice1= mutableStateOf( coins.first().name)
                    choice2= mutableStateOf( coins.first().name)
                }
            }
        }
    }

    fun setChoice1(name:String){
        choice1.value=name
    }
    fun setChoice2(name:String){
        choice2.value= name
    }

    private fun getCryptoValueByName(name:String): CoinModelAPIDB?{
        return coinList.value?.find{
            it.name==name
        }
    }
    fun calculate(){
        val myCrypto1= getCryptoValueByName(choice1.value)
        val myCrypto2=getCryptoValueByName(choice2.value)


        if (myCrypto1 == null || myCrypto2 == null) {
            Log.e("ConverterViewModel", "myCrypto1 or myCrypto2 is null")
            secondField.value = "error"
            return
        }
        if (myCrypto1.current_price.toFloatOrNull()==null || myCrypto2.current_price.toFloatOrNull()==null){
            Log.e("ConverterViewModel", "price1 or price 2 is null")
            secondField.value = "error"
            return
        }

        if(firstField.value.toFloatOrNull()!=null){
            val secondValueInDollars=firstField.value.toFloat()*myCrypto1.current_price.toFloat()
            Log.e("ConverterViewModel", "myCripto1:=$myCrypto1\nmyCrypto2:=$myCrypto2\nfirst_current_price:=${myCrypto1.current_price}\nsecondValueInDollars:= $secondValueInDollars\nFirstField value:= ${firstField.value}")
            val secondValueInCoin=secondValueInDollars/myCrypto2.current_price.toFloat()
            secondField.value= secondValueInCoin.toString()
        }
        else {
            Log.e("ConverterViewModel", "fieldValue empty")
            secondField.value = "error"
        }
    }
}