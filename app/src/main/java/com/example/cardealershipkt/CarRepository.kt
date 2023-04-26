package com.example.cardealershipkt

import android.content.Context
import androidx.lifecycle.LiveData

interface CarRepository {
    fun getCars(): LiveData<List<CarItem>>
    fun getCar(id: Long): LiveData<CarItem>
}