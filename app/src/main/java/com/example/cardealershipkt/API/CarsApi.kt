package com.example.cardealershipkt.API

import com.example.cardealershipkt.CarItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CarsApi {
    @get:GET("/CarInfo/get-all")
    val getAllCars: Call<List<CarItem>>

    @POST("/CarInfo/save")
    fun saveCar(@Body car: CarItem): Call<CarItem>
}
