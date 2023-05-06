package com.example.cardealershipkt.presentation.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.cardealershipkt.data.Room.CarItem
import com.example.cardealershipkt.data.CarRepositoryImpl
import com.example.cardealershipkt.domain.usecases.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference

class MainViewModel(
    private val getCarsCompilationUseCase: GetCarsCompilationUseCase,
    private val getCarsUseCase: GetCarsUseCase,
    private val getCarUseCase: GetCarUseCase,
    private val getFirebaseDatabaseUseCase: GetFirebaseDatabaseUseCase,
    private val refreshCarsUseCase: RefreshCarsUseCase,
) : ViewModel() {

    val carListLiveData = getCarsUseCase.getCars()
    val adminCode = "12345"

    init {
        refreshCars()
    }

    fun getFirebaseDatabase(pathString: String): DatabaseReference {
        return getFirebaseDatabaseUseCase.getFirebaseDatabase(pathString)
    }

    fun getUser(): FirebaseUser?{
        return FirebaseAuth.getInstance().currentUser
    }

    fun getAuth(): FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    fun getCarsCompilation(body: String): LiveData<List<CarItem>>{
        return getCarsCompilationUseCase.getCarsCompilation(body)
    }

    suspend fun getCar(id: Int): CarItem {
        return getCarUseCase.getCar(id)
    }

    fun refreshCars(){
        refreshCarsUseCase.refreshCars()
    }
}