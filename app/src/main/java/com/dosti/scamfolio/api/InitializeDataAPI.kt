package com.dosti.scamfolio.api

import com.dosti.scamfolio.dbStuff.Repository

suspend fun initializeDataAPI(repository: Repository) {

    val newCoin = ConnectionRetrofit.callApi().getAllCoins(
        "CG-9CHDGjAiUnv7oCnbFEB7KPAN",
        vsCurrency = "eur",
        order = "market_cap_desc",
        perPage = "250",
        sparklineBoolean = false
    )

    newCoin.find { it.autoID == 0 }?.time_fetched = System.currentTimeMillis()

    newCoin.let {
        for (coinData in it) {
            repository.insertCoinAPI(it)
        }
    }


}
