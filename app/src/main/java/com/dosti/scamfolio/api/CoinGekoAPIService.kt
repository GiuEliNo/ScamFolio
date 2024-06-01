package com.dosti.scamfolio.api

import androidx.lifecycle.MutableLiveData
import com.dosti.scamfolio.api.model.CoinModelAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Header

interface CoinGekoAPIService {
    @GET("coins/markets")
    suspend fun getAllCoins(
        @Header("secret_key") secretKey : String = "CG-9CHDGjAiUnv7oCnbFEB7KPAN",
        @retrofit2.http.Query("vs_currency") vsCurrency: String = "eur",
        @retrofit2.http.Query("order") order: String = "market_cap_desc",
        @retrofit2.http.Query("per_page") perPage: String = "250",
        @retrofit2.http.Query("sparkline") sparklineBoolean: Boolean = false
    ): MutableList<CoinModelAPI>
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
object CoinGekoAPI {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.coingecko.com/api/v3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val coinGekoAPIService: CoinGekoAPIService = retrofit.create(CoinGekoAPIService::class.java)
}

