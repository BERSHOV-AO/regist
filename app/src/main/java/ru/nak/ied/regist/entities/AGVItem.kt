package ru.nak.ied.regist.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "agv_table")
data class AGVItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "serialNumber")
    val serialNumber: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "maintenanceTime")
    val maintenanceTime: String
) : Serializable
