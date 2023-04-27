package com.example.cardealershipkt

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var currentUser = FirebaseAuth.getInstance().currentUser
    var mDataBase = FirebaseDatabase.getInstance()
    private val repository = CarRepository()
    private var car: CarItem? = null
    val carListLiveData = repository.getCars()

    init {
        refreshCars()
    }

    fun getFirebaseDatabase(pathString: String): DatabaseReference {
        return mDataBase.reference.child(pathString)
    }

    fun getCar(id: Int): LiveData<CarItem> {
        return repository.getCar(id)
    }

    fun refreshCars(){
        repository.refreshCars();
    }
}