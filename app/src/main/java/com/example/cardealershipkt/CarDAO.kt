package com.example.cardealershipkt

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CarDAO {
    @Query("SELECT * FROM carItem")
    fun getCars(): LiveData<List<CarItem>>
    @Query("SELECT * FROM carItem WHERE id = (:id)")
    fun getCar(id: Int): LiveData<CarItem>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(items: List<CarItem>)
}