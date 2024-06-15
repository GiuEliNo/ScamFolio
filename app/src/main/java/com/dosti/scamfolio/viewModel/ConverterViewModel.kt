package com.dosti.scamfolio.viewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dosti.scamfolio.db.entities.CoinModelAPIDB
import com.dosti.scamfolio.dbStuff.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ConverterViewModel(private val repository: Repository,private val sharedCoinGeko: SharedCoinGekoViewModel): ViewModel() {


    private val _coinList= MutableStateFlow<List<CoinModelAPIDB>?>(emptyList())
    var coinList: StateFlow<List<CoinModelAPIDB>?> = _coinList
    //var firstField = mutableStateOf("")
    private val _firstField: MutableStateFlow<String> = MutableStateFlow("")

    val firstField: StateFlow<String>
        get() = _firstField

    private val _secondField: MutableStateFlow<String> = MutableStateFlow("")

    val secondField: StateFlow<String>
        get() = _secondField



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

    fun setSecondField(name:String){
        _secondField.value=name
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
            setSecondField("error")
            return
        }
        if (myCrypto1.current_price.toFloatOrNull()==null || myCrypto2.current_price.toFloatOrNull()==null){
            Log.e("ConverterViewModel", "price1 or price 2 is null")
            setSecondField("error")
            return
        }

        val inTextValue = runBlocking { firstField.first().toDoubleOrNull() ?: 0.0 }

        Log.e("ConverterViewModel", inTextValue.toString())


        val secondValueInDollars = inTextValue * myCrypto1.current_price.toFloat()
        Log.e("ConverterViewModel", "myCripto1:=$myCrypto1\nmyCrypto2:=$myCrypto2\nfirst_current_price:=${myCrypto1.current_price}\nsecondValueInDollars:= $secondValueInDollars\nFirstField value:= ${firstField.value}")
        val secondValueInCoin=secondValueInDollars/myCrypto2.current_price.toFloat()
        setSecondField(secondValueInCoin.toString())
    }

    fun setfirstField(filtered: String) {
        _firstField.value = filtered
    }
}