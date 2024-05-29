package com.dosti.scamfolio.model.api

import com.dosti.scamfolio.model.Coin

fun getCoin() : Coin {
    val coin = Coin("Bitcoin", "BTC", 1000, "+2,30%")
    return coin
}

//andrà implementata una chiamata API