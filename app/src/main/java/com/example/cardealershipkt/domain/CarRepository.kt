package com.example.cardealershipkt.domain

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.cardealershipkt.data.API.CarsApi
import com.example.cardealershipkt.data.API.RetrofitService
import com.example.cardealershipkt.data.Room.CarItem
import com.google.firebase.database.DatabaseReference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface CarRepository {
    fun getCars(): LiveData<List<CarItem>>
    suspend fun getCar(id: Int): CarItem
    fun getCarsCompilation(body: String): LiveData<List<CarItem>>
    fun getFirebaseDatabase(pathString: String): DatabaseReference
    fun saveCarsToDatabase(cars: List<CarItem>)
    fun refreshCars()
}