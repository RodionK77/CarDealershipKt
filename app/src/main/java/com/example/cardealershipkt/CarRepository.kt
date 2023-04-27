package com.example.cardealershipkt

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.cardealershipkt.API.CarsApi
import com.example.cardealershipkt.API.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors

class CarRepository {

    private val database: CarDatabase = CarDatabase.get()

    private val carDao = database.carDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getCars(): LiveData<List<CarItem>> = carDao.getCars()
    fun getCar(id: Int): LiveData<CarItem> = carDao.getCar(id)
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
                    print("Данные ЕСТЬ")
                    saveCarsToDatabase(response.body()!!)
                }

                override fun onFailure(call: Call<List<CarItem>>, t: Throwable) {
                    Log.v("ViewModelRef", "Данные не загрузились", t)
                    print("Данные не загрузились")
                }
            })
    }

}