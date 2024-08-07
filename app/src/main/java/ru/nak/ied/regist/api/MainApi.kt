package ru.nak.ied.regist.api

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.nak.ied.regist.entities.AGVItem
import ru.nak.ied.regist.entities.AgvDiagnostic
import ru.nak.ied.regist.entities.LogAgv
import ru.nak.ied.regist.entities.ModelAGV
import ru.nak.ied.regist.entities.NameTO
import ru.nak.ied.regist.entities.User
import ru.nak.ied.regist.utils.TOData

interface MainApi {

    /**
     * ***************************************DELETE**********************************************
     */
    @DELETE("delete_agv_by_sn.php")
    suspend fun deleteAgvBySerialNumber(@Query("serialNumber") serialNumber: String): List<AGVItem>

    @GET("delete_to_agv_by_sn.php")
    suspend fun deleteAgvTOBySerialNumber(@Query("serialNumberAGV") serialNumber: String): List<TOData>

    /**
     * ***************************************SAVE**********************************************
     */

    @POST("save_user_agv.php")
    suspend fun saveUser(@Body user: User)

    @POST("save_diagnostic_agv.php")
    suspend fun saveDiagnosticAgv(@Body agvDiagnostic: AgvDiagnostic)

    @POST("save_agv.php")
    suspend fun saveAGV(@Body agvItem: AGVItem)

    @POST("save_model_agv.php")
    suspend fun saveModelAGV(@Body modelAGV: ModelAGV)

    @POST("save_log_agv.php")
    suspend fun saveLogAgv(@Body logAgv: LogAgv)

    @POST("save_toagv.php")
    suspend fun saveAgvTo(@Body nameTO: NameTO)

    @POST("update_agv_to.php")
    suspend fun updateAgvTo(@Body nameTO: NameTO)

    @POST("save_or_update_agv.php")
    suspend fun saveOrUpdateAgv(@Body agvItem: AGVItem)

    /**
     * ***************************************GET**********************************************
     */
    @GET("get_agv_to_by_sn.php")
    suspend fun getTOAgvBySN(@Query("serialNumberAGV") serialNumberAGV: String): List<NameTO>

    //-------------------------------------------------------------------------
    @GET("get_agv_to_by_sn_and_status_1.php")
    suspend fun getTOAgvBySNAndStatus_1(
        @Query("serialNumberAGV") serialNumberAGV: String
    ): List<NameTO>

    @GET("get_agv_to_by_sn_and_status_0.php")
    suspend fun getTOAgvBySNAndStatus_0(
        @Query("serialNumberAGV") serialNumberAGV: String
    ): List<NameTO>

    @GET("get_agv_to_by_sn_and_status_2.php")
    suspend fun getTOAgvBySNAndStatus_2(
        @Query("serialNumberAGV") serialNumberAGV: String
    ): List<NameTO>

    @GET("get_agv_to_by_sn_and_status.php")
    suspend fun getTOAgvBySNAndStatus(
        @Query("serialNumberAGV") serialNumberAGV: String
    ): List<NameTO>

    //----------------------------------------------------------------------------------

    @GET("get_all_users.php")
    suspend fun getAllUsers(): List<User>

    @GET("get_all_log.php")
    suspend fun getAllLog(): List<LogAgv>

    @GET("get_all_diagnostic_agv.php")
    suspend fun getAllDiagnosticAgv(): List<AgvDiagnostic>

    @GET("get_all_agv.php")
    suspend fun getAllAGV(): List<AGVItem>

    @GET("get_all_model_agv.php")
    suspend fun getAllModelAGV(): List<ModelAGV>

    @GET("get_all_agv_to.php")
    suspend fun getAllAgvTo(): List<NameTO>

    @GET("test_connect_db.php")
    suspend fun getConnectDB(): Boolean

    @GET("user_presence.php")
    suspend fun getUserExistence(@Body login: String, @Body pass: String): Boolean
}