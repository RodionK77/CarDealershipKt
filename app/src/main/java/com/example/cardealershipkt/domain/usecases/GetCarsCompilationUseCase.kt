package com.example.cardealershipkt.domain.usecases

import androidx.lifecycle.LiveData
import com.example.cardealershipkt.data.Room.CarItem
import com.example.cardealershipkt.domain.CarRepository

class GetCarsCompilationUseCase(private val repository: CarRepository) {

    fun getCarsCompilation(body: String): LiveData<List<CarItem>> {
        return repository.getCarsCompilation(body)
    }
}