package com.example.cardealershipkt.domain
import java.io.Serializable

class User: Serializable {

    var name: String = ""
    var lastname: String= ""
    var email: String= ""
    var birthday: String= ""
    var phone: String= ""
    var role: String= ""
    var uid: String= ""
    var bookstores: String= ""

    constructor()

    constructor(
        name: String,
        lastname: String,
        email: String,
        birthday: String,
        phone: String,
        role: String,
        uid: String,
        bookstores: String){
        this.name = name
        this.lastname = lastname
        this.email = email
        this.birthday = birthday
        this.phone = phone
        this.role = role
        this.uid = uid
        this.bookstores = bookstores
    }


    fun addBookstores(num: Int) {
        bookstores += "$num-"
    }

    fun delBookstores(num: Int) {
        bookstores = bookstores.replace("$num-", "")
    }
}