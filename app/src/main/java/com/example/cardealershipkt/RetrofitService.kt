package com.example.cardealershipkt

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitService {
    var retrofit: Retrofit? = null
        private set

    init {
        initializeRetrofit()
    }

    private fun initializeRetrofit() {
        retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.48:8080")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
    }
}