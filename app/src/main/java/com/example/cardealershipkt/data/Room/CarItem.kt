package com.example.cardealershipkt.data.Room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class CarItem (@PrimaryKey val id: Int, val brand: String, val name: String, val price: Int,
                    val color: String, val body: String, val capacity: Float, val power: Int,
                    val transmission: String, val engine: String, val drive: String, val acceleration: Float,
                    val consumption: Float, val country: String, val car_class: String, val doors: Int,
                    val places: Int, val wheel: String, val length: Int, val width: Int,
                    val height: Int, val trunk: Int, val fuel_tank: Int, val image: String) : Serializable