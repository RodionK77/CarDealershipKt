package com.example.cardealershipkt
import java.io.Serializable
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.GenericTypeIndicator

class User (var name: String,
            var lastname: String,
            var email: String,
            var birthday: String,
            var phone: String,
            var role: String,
            var uid: String,
            var bookstores: String): Serializable {

    fun addBookstores(num: Int) {
        bookstores += num.toString()
    }

    fun delBookstores(num: Int) {
        bookstores = bookstores.replace(num.toString(), "")
    }
}