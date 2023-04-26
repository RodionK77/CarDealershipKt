package com.example.cardealershipkt

import androidx.lifecycle.LiveData
import java.util.concurrent.Executors

class CarRepositoryImpl : CarRepository {

    private val database: CarDatabase = CarDatabase.get()

    private val carDao = database.carDao()
    private val executor = Executors.newSingleThreadExecutor()

    override fun getCars(): LiveData<List<CarItem>> = carDao.getTodos()
    override fun getCar(id: Long): LiveData<CarItem> = carDao.getTodo(id)

}