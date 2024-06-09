package com.dosti.scamfolio.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ConnectionRetrofit {
    companion object {

        private fun getRetrofit(url:String): Retrofit {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .build()
        }

        private fun getApiData():Retrofit{
            val retrofitApi = getRetrofit(UrlCoinGeko.BASE_URL)
            return retrofitApi
        }

        fun callApi():CoinGekoAPIService{
            val retrofitCall = getApiData()
            return retrofitCall.create(CoinGekoAPIService::class.java)
        }

    }
}