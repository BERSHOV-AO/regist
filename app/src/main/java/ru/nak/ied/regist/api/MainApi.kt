package ru.nak.ied.regist.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import ru.nak.ied.regist.entities.AGVItem
import ru.nak.ied.regist.entities.User

interface MainApi {

    @POST("save_user_agv.php")
    suspend fun saveUser(@Body user: User)

    @GET("get_all_users.php")
    suspend fun getAllUsers(): List<User>

    @GET("get_all_agv.php")
    suspend fun getAllAGV(): List<AGVItem>

    @GET("test_connect_db.php")
    suspend fun getConnectDB(): Boolean

    @GET("user_presence.php")
    suspend fun getUserExistence(@Body login: String, @Body pass: String): Boolean


}