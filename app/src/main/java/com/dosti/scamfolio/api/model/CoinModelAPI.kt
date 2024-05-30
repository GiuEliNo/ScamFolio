package com.dosti.scamfolio.api.model

data class CurrentPrice (
    val usd : String,
    val eur : String
)

data class MarketData(
    val price_change_percentage_24h : String,
    val current_price : CurrentPrice
)
data class IconCoin(
    val small: String
)
data class CoinModelAPI(
    val name: String,
    val symbol: String,
    val price: String,
    val image: IconCoin,
    val market_data : MarketData
)

