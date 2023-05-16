package com.example.cardealershipkt.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cardealershipkt.data.Room.CarItem
import com.example.cardealershipkt.domain.CarRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetCarUseCaseTest {
    @get:Rule
    private val testDispatcher = StandardTestDispatcher()

    private val repository = mock<CarRepository>()

    @Test
    fun shouldReturnCorrectDataConv() = runTest {
        val testCar = CarItem(12, "BMW", "F36", 3690000, "Синий", "Кабриолет", 2.0f, 245, "Автомат", "Бензин", "Задний", 6.4f, 6.6f, "Германия", "D", 2, 4, "Лево", 4638, 1825, 1384, 370, 57, "url")
        Mockito.`when`(repository.getCar(12)).thenReturn(testCar)

        val useCase = GetCarUseCase(repository)
        val actual = useCase.getCar(12)

        Assert.assertEquals(testCar, actual)
    }
}