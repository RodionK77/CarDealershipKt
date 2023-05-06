package com.example.cardealershipkt.domain.usecases

import com.example.cardealershipkt.domain.CarRepository
import com.google.firebase.database.DatabaseReference

class GetFirebaseDatabaseUseCase(private val repository: CarRepository) {

    fun getFirebaseDatabase(pathString: String): DatabaseReference {
        return repository.getFirebaseDatabase(pathString)
    }
}