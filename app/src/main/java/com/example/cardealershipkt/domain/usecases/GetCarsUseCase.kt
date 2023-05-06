package com.example.cardealershipkt.domain.usecases

import androidx.lifecycle.LiveData
import com.example.cardealershipkt.data.Room.CarItem
import com.example.cardealershipkt.domain.CarRepository

class GetCarsUseCase(private val repository: CarRepository) {

    fun getCars(): LiveData<List<CarItem>> {
        return repository.getCars()
    }
}