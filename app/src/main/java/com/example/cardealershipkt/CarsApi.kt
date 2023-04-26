package com.example.cardealershipkt

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CarsApi {
    @get:GET("/car/get-all")
    val getAllCars: Call<List<CarItem>>

    @POST("/car/save")
    fun saveCar(@Body car: CarItem): Call<CarItem>
}
