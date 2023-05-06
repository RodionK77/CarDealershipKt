package com.example.cardealershipkt.presentation.ViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cardealershipkt.data.CarRepositoryImpl
import com.example.cardealershipkt.domain.CarRepository
import com.example.cardealershipkt.domain.usecases.*

class MainViewModelFactory(context: Context): ViewModelProvider.Factory {

    private val repository: CarRepository = CarRepositoryImpl()
    private val getCarsCompilationUseCase = GetCarsCompilationUseCase(repository)
    private val getCarsUseCase = GetCarsUseCase(repository)
    private val getCarUseCase = GetCarUseCase(repository)
    private val getFirebaseDatabaseUseCase = GetFirebaseDatabaseUseCase(repository)
    private val refreshCarsUseCase = RefreshCarsUseCase(repository)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(getCarsCompilationUseCase,
            getCarsUseCase, getCarUseCase, getFirebaseDatabaseUseCase, refreshCarsUseCase) as T
    }


}