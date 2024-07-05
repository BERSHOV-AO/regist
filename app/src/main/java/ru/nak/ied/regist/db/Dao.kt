//package ru.nak.ied.regist.db
//
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.Query
//import androidx.room.Update
//import kotlinx.coroutines.flow.Flow
//import ru.nak.ied.regist.entities.AGVItem
//
//@Dao
//interface Dao {
//
//    @Query("SELECT * FROM agv_table")
//    fun getAllAGV(): Flow<List<AGVItem>>
//
//    @Query("DELETE FROM agv_table WHERE id IS :id")
//    suspend fun deleteAGV(id: Int)
//
//    @Insert
//    suspend fun insertAGV(agv: AGVItem)
//
//    @Update
//    suspend fun updateAGV(agv: AGVItem)
//}