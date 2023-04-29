package com.example.cardealershipkt

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repository = CarRepository()
    val carListLiveData = repository.getCars()
    val adminCode = "12345"

    init {
        refreshCars()
    }

    fun getFirebaseDatabase(pathString: String): DatabaseReference {
        return repository.getFirebaseDatabase(pathString)
    }

    fun getUser(): FirebaseUser?{
        return FirebaseAuth.getInstance().currentUser
    }

    fun getAuth(): FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    fun getCarsCompilation(body: String): LiveData<List<CarItem>>{
        return repository.getCarsCompilation(body)
    }

    suspend fun getCar(id: Int): CarItem {
        return repository.getCar(id)
    }

    fun refreshCars(){
        repository.refreshCars()
    }
}