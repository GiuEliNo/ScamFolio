package com.dosti.scamfolio.api

import com.dosti.scamfolio.api.model.CoinModelAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Header

interface CoinGekoAPIService {
    @GET("coins/{id}")
    suspend fun getCoinData(
        @Header("secret_key") secretKey : String = "CG-9CHDGjAiUnv7oCnbFEB7KPAN",
        @Path("id") coinId: String,
        @retrofit2.http.Query("market_data") marketDataBoolean: Boolean = true,
        @retrofit2.http.Query("community_data") communityDataBoolean: Boolean = false,
        @retrofit2.http.Query("developer_data") devDataBoolean: Boolean = false,
        @retrofit2.http.Query("sparkline") sparklineBoolean: Boolean = false
    ): CoinModelAPI
}
object CoinGekoAPI {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.coingecko.com/api/v3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val coinGekoAPIService: CoinGekoAPIService = retrofit.create(CoinGekoAPIService::class.java)
}

