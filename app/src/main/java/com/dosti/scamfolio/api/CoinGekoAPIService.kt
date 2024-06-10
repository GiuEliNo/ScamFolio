package com.dosti.scamfolio.api

import com.dosti.scamfolio.api.model.CoinModelAPI
import com.dosti.scamfolio.api.model.CoinModelAPIDB
import retrofit2.http.GET
import retrofit2.http.Header

interface CoinGekoAPIService {
    @GET("coins/markets")
    suspend fun getAllCoins(
        @Header("secret_key") secretKey : String = "CG-9CHDGjAiUnv7oCnbFEB7KPAN",
        @retrofit2.http.Query("vs_currency") vsCurrency: String = "eur",
        @retrofit2.http.Query("order") order: String = "market_cap_desc",
        @retrofit2.http.Query("per_page") perPage: String = "250",
        @retrofit2.http.Query("sparkline") sparklineBoolean: Boolean = false
    ): MutableList<CoinModelAPIDB>
    @GET("coins/markets")
    suspend fun getCoinData(
        @Header("secret_key") secretKey : String = "CG-9CHDGjAiUnv7oCnbFEB7KPAN",
        @retrofit2.http.Query("ids") id: String,
        @retrofit2.http.Query("vs_currency") vsCurrency: String = "eur",
        @retrofit2.http.Query("order") order: String = "market_cap_desc",
        @retrofit2.http.Query("per_page") perPage: String = "250",
        @retrofit2.http.Query("sparkline") sparklineBoolean: Boolean = false
    ): List<CoinModelAPI>
}