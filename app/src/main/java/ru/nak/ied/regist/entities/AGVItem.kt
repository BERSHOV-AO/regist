package ru.nak.ied.regist.entities

data class AGVItem(
    val id: Int?,
    val name: String,
    val serialNumber: String,
    val description: String,
    val maintenanceTime: String
)