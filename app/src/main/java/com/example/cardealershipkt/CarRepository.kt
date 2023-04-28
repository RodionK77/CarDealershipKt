package com.example.cardealershipkt

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.cardealershipkt.API.CarsApi
import com.example.cardealershipkt.API.RetrofitService
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