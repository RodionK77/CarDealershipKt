package com.example.cardealershipkt.domain.usecases

import com.example.cardealershipkt.domain.CarRepository

class RefreshCarsUseCase(private val repository: CarRepository) {

    fun refreshCars(){
        repository.refreshCars()
    }
}