package ru.nak.ied.regist.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.nak.ied.regist.entities.AGVItem
import ru.nak.ied.regist.entities.AgvDiagnostic
import ru.nak.ied.regist.entities.DirComposition
import ru.nak.ied.regist.entities.ImageData
import ru.nak.ied.regist.entities.ImageUploadResponse
import ru.nak.ied.regist.entities.LogAgv
import ru.nak.ied.regist.entities.ModelAGV
import ru.nak.ied.regist.entities.NameAndFrequencyTO
import ru.nak.ied.regist.entities.NameTO
import ru.nak.ied.regist.entities.User
import ru.nak.ied.regist.utils.TOData

interface MainApi {

    @GET("download_pdf.php") // Укажите путь к вашему PHP-скрипту
    suspend fun downloadPdf(): Call<ResponseBody>

    /**
     * ***************************************DELETE**********************************************
     */
    @DELETE("delete_agv_by_sn.php")
    suspend fun deleteAgvBySerialNumber(@Query("serialNumber") serialNumber: String):
            List<AGVItem>

    @GET("delete_to_agv_by_sn.php")
    suspend fun deleteAgvTOBySerialNumber(@Query("serialNumberAGV") serialNumber: String):
            List<TOData>

    /**
     * ***************************************SAVE**********************************************
     */

    //-------------------------------------------image----------------------------------------------
    @POST("upload_image.php")
    suspend fun uploadImage(@Body imageData: ImageData): ImageUploadResponse

    @GET("get_image.php")
    suspend fun getImage(@Query("name") imageName: String): Response<ResponseBody>

    @GET("get_scheme_AGV_0002_00_01_00_001.php")
    suspend fun getScheme_AGV_0002_00_01_00_001(@Query("name") imageName: String):
            Response<ResponseBody>

    @GET("get_scheme_AGV_0002_00_01_00_001_a.php")
    suspend fun getScheme_AGV_0002_00_01_00_001a(@Query("name") imageName: String):
            Response<ResponseBody>

    //----------------------------------------------------------------------------------------------
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

    @GET("get_dir_fw.php")
    suspend fun getDir(): DirComposition

    @GET("get_dir_and_files_schemes.php")
    suspend fun getDirAndFilesSchemes(): DirComposition

    //---------------------------------------by save agv to-----------------------------------------
    @GET("get_to_data_agv_1100_2p.php")
    suspend fun getT0DataAgv11002P(): List<NameAndFrequencyTO>

    @GET("get_to_data_agv_1100_st.php")
    suspend fun getT0DataAgv1100St(): List<NameAndFrequencyTO>

    @GET("get_to_data_agv_1100_2t.php")
    suspend fun getT0DataAgv11002t(): List<NameAndFrequencyTO>

    @GET("get_to_data_agv_3000_st.php")
    suspend fun getT0DataAgv3000St(): List<NameAndFrequencyTO>

    @GET("get_agv_to_by_sn.php")
    suspend fun getTOAgvBySN(@Query("serialNumberAGV") serialNumberAGV: String): List<NameTO>

    //----------------------------------------------------------------------------------------------
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

    //----------------------------------------------------------------------------------------------

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