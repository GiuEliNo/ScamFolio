package com.dosti.scamfolio.model.api

import com.dosti.scamfolio.model.Coin

fun getCoin() : Coin {
    val coin = Coin("Bitcoin", "BTC", 1000, "+2,30%", "https://assets.coingecko.com/coins/images/1/standard/bitcoin.png" )
    return coin
}

//andrà implementata una chiamata API