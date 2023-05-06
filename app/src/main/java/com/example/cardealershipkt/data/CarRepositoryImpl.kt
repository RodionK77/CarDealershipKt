package com.example.cardealershipkt.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.cardealershipkt.data.API.CarsApi
import com.example.cardealershipkt.data.API.RetrofitService
import com.example.cardealershipkt.data.Room.CarDatabase
import com.example.cardealershipkt.data.Room.CarItem
import com.example.cardealershipkt.domain.CarRepository
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors

class CarRepositoryImpl : CarRepository {

    private val database: CarDatabase = CarDatabase.get()
    var mDataBase = FirebaseDatabase.getInstance()

    private val carDao = database.carDao()
    private val executor = Executors.newSingleThreadExecutor()

    override fun getCars(): LiveData<List<CarItem>> = carDao.getCars()
    override suspend fun getCar(id: Int): CarItem = carDao.getCar(id)
    override fun getCarsCompilation(body: String): LiveData<List<CarItem>> = carDao.getCarsCompilation(body)
    override fun getFirebaseDatabase(pathString: String): DatabaseReference {
        return mDataBase.reference.child(pathString)
    }
    override fun saveCarsToDatabase(cars: List<CarItem>) {
        executor.execute{
            carDao.saveAll(cars)
        }
    }
    override fun refreshCars(){
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