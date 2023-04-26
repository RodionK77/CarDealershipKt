package com.example.cardealershipkt

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CarDAO {
    @Query("SELECT * FROM carItem")
    fun getTodos(): LiveData<List<CarItem>>
    @Query("SELECT * FROM carItem WHERE id = (:id)")
    fun getTodo(id: Long): LiveData<CarItem>
}