package ru.nak.ied.regist.entities

data class AGVItem(
    val id: Int?,
    val name: String,
    val serialNumber: String,
    val versionFW: String,
    val model: String,
    val ePlan: String,
    val dataLastTo: String
)