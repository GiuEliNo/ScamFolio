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

    // Assuming the newCoin is a list of coin data
    newCoin.let {
        for (coinData in it) {
            repository.insertCoinAPI(it)
        }
    }

}
