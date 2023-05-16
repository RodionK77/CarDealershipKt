package com.example.cardealershipkt.domain.usecases

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cardealershipkt.data.Room.CarItem
import com.example.cardealershipkt.domain.CarRepository
import com.google.firebase.database.DatabaseReference
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class GetCarsCompilationTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val repository = mock<CarRepository>()

    @Test
    fun shouldReturnCorrectDataConv(){

        val testCar1 = CarItem(12, "BMW", "F36", 3690000, "Синий", "Кабриолет", 2.0f, 245, "Автомат", "Бензин", "Задний", 6.4f, 6.6f, "Германия", "D", 2, 4, "Лево", 4638, 1825, 1384, 370, 57, "url")
        val testCar2 = CarItem(13, "BMW2", "F36", 3690000, "Синий", "Кабриолет", 2.0f, 245, "Автомат", "Бензин", "Задний", 6.4f, 6.6f, "Германия", "D", 2, 4, "Лево", 4638, 1825, 1384, 370, 57, "url")
        val carList = listOf(testCar1, testCar2)
        val carLiveData: LiveData<List<CarItem>> = MutableLiveData<List<CarItem>>().apply{
            postValue(carList)
        }
        Mockito.`when`(repository.getCarsCompilation(body = "Кабриолет")).thenReturn(carLiveData)

        val useCase = GetCarsCompilationUseCase(repository)
        val actual = useCase.getCarsCompilation("Кабриолет")

        Assert.assertEquals(carLiveData, actual)
    }

    @Test
    fun shouldReturnCorrectDataOff(){

        val testCar1 = CarItem(12, "BMW", "F36", 3690000, "Синий", "Внедорожник", 2.0f, 245, "Автомат", "Бензин", "Задний", 6.4f, 6.6f, "Германия", "D", 2, 4, "Лево", 4638, 1825, 1384, 370, 57, "url")
        val testCar2 = CarItem(13, "BMW2", "F36", 3690000, "Синий", "Внедорожник", 2.0f, 245, "Автомат", "Бензин", "Задний", 6.4f, 6.6f, "Германия", "D", 2, 4, "Лево", 4638, 1825, 1384, 370, 57, "url")
        val carList = listOf(testCar1, testCar2)
        val carLiveData: LiveData<List<CarItem>> = MutableLiveData<List<CarItem>>().apply{
            postValue(carList)
        }
        Mockito.`when`(repository.getCarsCompilation(body = "Внедорожник")).thenReturn(carLiveData)

        val useCase = GetCarsCompilationUseCase(repository)
        val actual = useCase.getCarsCompilation("Внедорожник")

        Assert.assertEquals(carLiveData, actual)
    }

    @Test
    fun shouldReturnCorrectDataSedan(){

        val testCar1 = CarItem(12, "BMW", "F36", 3690000, "Синий", "Седан", 2.0f, 245, "Автомат", "Бензин", "Задний", 6.4f, 6.6f, "Германия", "D", 2, 4, "Лево", 4638, 1825, 1384, 370, 57, "url")
        val testCar2 = CarItem(13, "BMW2", "F36", 3690000, "Синий", "Седан", 2.0f, 245, "Автомат", "Бензин", "Задний", 6.4f, 6.6f, "Германия", "D", 2, 4, "Лево", 4638, 1825, 1384, 370, 57, "url")
        val carList = listOf(testCar1, testCar2)
        val carLiveData: LiveData<List<CarItem>> = MutableLiveData<List<CarItem>>().apply{
            postValue(carList)
        }
        Mockito.`when`(repository.getCarsCompilation(body = "Седан")).thenReturn(carLiveData)

        val useCase = GetCarsCompilationUseCase(repository)
        val actual = useCase.getCarsCompilation("Седан")

        Assert.assertEquals(carLiveData, actual)
    }

    @Test
    fun shouldReturnCorrectDataClassic(){

        val testCar1 = CarItem(12, "BMW", "F36", 3690000, "Синий", "Спорт классика", 2.0f, 245, "Автомат", "Бензин", "Задний", 6.4f, 6.6f, "Германия", "D", 2, 4, "Лево", 4638, 1825, 1384, 370, 57, "url")
        val testCar2 = CarItem(13, "BMW2", "F36", 3690000, "Синий", "Спорт классика", 2.0f, 245, "Автомат", "Бензин", "Задний", 6.4f, 6.6f, "Германия", "D", 2, 4, "Лево", 4638, 1825, 1384, 370, 57, "url")
        val carList = listOf(testCar1, testCar2)
        val carLiveData: LiveData<List<CarItem>> = MutableLiveData<List<CarItem>>().apply{
            postValue(carList)
        }
        Mockito.`when`(repository.getCarsCompilation(body = "Спорт классика")).thenReturn(carLiveData)

        val useCase = GetCarsCompilationUseCase(repository)
        val actual = useCase.getCarsCompilation("Спорт классика")

        Assert.assertEquals(carLiveData, actual)
    }
}