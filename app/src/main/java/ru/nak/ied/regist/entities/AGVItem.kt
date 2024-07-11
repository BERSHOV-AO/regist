package ru.nak.ied.regist.entities

data class AGVItem(
    val id: Int?,
    val name: String,
    val serialNumber: String,
    val description: String,
    val maintenanceTime: String
)

//@Entity(tableName = "agv_table")
//data class AGVItem(
//    @PrimaryKey(autoGenerate = true)
//    val id: Int?,
//    @ColumnInfo(name = "name")
//    val name: String,
//    @ColumnInfo(name = "serialNumber")
//    val serialNumber: String,
//    @ColumnInfo(name = "description")
//    val description: String,
//    @ColumnInfo(name = "maintenanceTime")
//    val maintenanceTime: String
//) : Serializable