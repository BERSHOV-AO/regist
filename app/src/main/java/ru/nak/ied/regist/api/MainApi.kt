package ru.nak.ied.regist.api

import retrofit2.http.Body
import retrofit2.http.GET
import ru.nak.ied.regist.entities.User

interface MainApi {

    @GET("get_all_users.php")
    suspend fun getAllUsers(): List<User>

    @GET("user_presence.php")
    suspend fun getUserExistence(@Body login: String, @Body pass: String): Boolean
}