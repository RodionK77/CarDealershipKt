package com.example.cardealershipkt.domain.usecases

import com.example.cardealershipkt.data.Room.CarItem
import com.example.cardealershipkt.domain.CarRepository

class GetCarUseCase(private val repository: CarRepository) {

    suspend fun getCar(id: Int): CarItem {
        return repository.getCar(id)
    }
}