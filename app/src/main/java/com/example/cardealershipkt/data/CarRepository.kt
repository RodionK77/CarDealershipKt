package com.example.cardealershipkt.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.cardealershipkt.data.API.CarsApi
import com.example.cardealershipkt.data.API.RetrofitService
import com.example.cardealershipkt.data.Room.CarDatabase
import com.example.cardealershipkt.data.Room.CarItem
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors

class CarRepository {

    private val database: CarDatabase = CarDatabase.get()
    var mDataBase = FirebaseDatabase.getInstance()

    private val carDao = database.carDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getCars(): LiveData<List<CarItem>> = carDao.getCars()
    suspend fun getCar(id: Int): CarItem = carDao.getCar(id)
    fun getCarsCompilation(body: String): LiveData<List<CarItem>> = carDao.getCarsCompilation(body)
    fun getFirebaseDatabase(pathString: String): DatabaseReference {
        return mDataBase.reference.child(pathString)
    }
    fun saveCarsToDatabase(cars: List<CarItem>) {
        executor.execute{
            carDao.saveAll(cars)
        }
    }
    fun refreshCars(){
        val retrofitService = RetrofitService()
        val carsApi: CarsApi = retrofitService.retrofit!!.create(CarsApi::class.java)
        carsApi.getAllCars
            .enqueue(object : Callback<List<CarItem>> {
                override fun onResponse(
                    call: Call<List<CarItem>>,
                    response: Response<List<CarItem>>
                ) {
                    response.body()?.let { saveCarsToDatabase(it) }
                }

                override fun onFailure(call: Call<List<CarItem>>, t: Throwable) {
                    Log.d("R", "Данные не загрузились", t)
                }
            })
    }

}