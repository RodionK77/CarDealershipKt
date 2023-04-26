package com.example.cardealershipkt

import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    var mAuth = FirebaseAuth.getInstance()
    var mDataBase = FirebaseDatabase.getInstance()
    var index = 0
    private lateinit var currentUser: FirebaseUser

    fun getFirebaseUser(): FirebaseUser? {
        return mAuth.currentUser
    }

    fun getFirebaseDatabase(pathString: String): DatabaseReference {
        return mDataBase.reference.child(pathString)
    }

    fun loadCars(){
        val retrofitService = RetrofitService()
        val carsApi: CarsApi = retrofitService.retrofit!!.create(CarsApi::class.java)
        carsApi.getAllCars
            .enqueue(object : Callback<List<CarItem>> {
                override fun onResponse(
                    call: Call<List<CarItem>>,
                    response: Response<List<CarItem>>
                ) {
                    populateListView(response.body())
                }

                override fun onFailure(call: Call<List<CarItem>>, t: Throwable) {
                    Toast.makeText(
                        this@EmployeeListActivity,
                        "Failed to load employees",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}