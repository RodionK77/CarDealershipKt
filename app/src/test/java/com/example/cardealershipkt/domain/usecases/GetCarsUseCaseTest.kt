package com.example.cardealershipkt.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cardealershipkt.data.Room.CarItem
import com.example.cardealershipkt.domain.CarRepository
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetCarsUseCaseTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repository = mock<CarRepository>()

    @Test
    fun shouldReturnCorrectData(){

        val testCar1 = CarItem(12, "BMW", "F36", 3690000, "Синий", "Кабриолет", 2.0f, 245, "Автомат", "Бензин", "Задний", 6.4f, 6.6f, "Германия", "D", 2, 4, "Лево", 4638, 1825, 1384, 370, 57, "url")
        val testCar2 = CarItem(13, "BMW2", "F36", 3690000, "Синий", "Кабриолет", 2.0f, 245, "Автомат", "Бензин", "Задний", 6.4f, 6.6f, "Германия", "D", 2, 4, "Лево", 4638, 1825, 1384, 370, 57, "url")
        val testCar3 = CarItem(12, "Audi", "F36", 3690000, "Синий", "Внедорожник", 2.0f, 245, "Автомат", "Бензин", "Задний", 6.4f, 6.6f, "Германия", "D", 2, 4, "Лево", 4638, 1825, 1384, 370, 57, "url")
        val testCar4 = CarItem(13, "Audi2", "F36", 3690000, "Синий", "Внедорожник", 2.0f, 245, "Автомат", "Бензин", "Задний", 6.4f, 6.6f, "Германия", "D", 2, 4, "Лево", 4638, 1825, 1384, 370, 57, "url")
        val carList = listOf(testCar1, testCar2, testCar3, testCar4)
        val carLiveData: LiveData<List<CarItem>> = MutableLiveData<List<CarItem>>().apply{
            postValue(carList)
        }
        Mockito.`when`(repository.getCars()).thenReturn(carLiveData)

        val useCase = GetCarsUseCase(repository)
        val actual = useCase.getCars()

        Assert.assertEquals(carLiveData, actual)
    }
}