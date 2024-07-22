package ru.nak.ied.regist.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.nak.ied.regist.entities.AGVItem
import ru.nak.ied.regist.entities.AgvDiagnostic
import ru.nak.ied.regist.entities.NameTO
import ru.nak.ied.regist.entities.User

interface MainApi {

    @POST("save_user_agv.php")
    suspend fun saveUser(@Body user: User)

    @POST("save_diagnostic_agv.php")
    suspend fun saveDiagnosticAgv(@Body agvDiagnostic: AgvDiagnostic)

    @POST("save_agv.php")
    suspend fun saveAGV(@Body agvItem: AGVItem)

    @POST("save_toagv.php")
    suspend fun saveAgvTo(@Body nameTO: NameTO)

    @POST("save_or_update_agv.php")
    suspend fun saveOrUpdateAgv(@Body agvItem: AGVItem)

//    @GET("get_diagnostic_agv_by_serial_number.php")
//    suspend fun getDiagnosticBySerialNum(@Query("serialNumber") serialNumber: String): AgvDiagnostic

    @GET("get_agv_to_by_sn.php")
    suspend fun getTOAgvBySN(@Query("serialNumberAGV") serialNumberAGV: String): List<NameTO>

    @GET("get_all_users.php")
    suspend fun getAllUsers(): List<User>

    @GET("get_all_diagnostic_agv.php")
    suspend fun getAllDiagnosticAgv(): List<AgvDiagnostic>

    @GET("get_all_agv.php")
    suspend fun getAllAGV(): List<AGVItem>

    @GET("test_connect_db.php")
    suspend fun getConnectDB(): Boolean

    @GET("user_presence.php")
    suspend fun getUserExistence(@Body login: String, @Body pass: String): Boolean
}