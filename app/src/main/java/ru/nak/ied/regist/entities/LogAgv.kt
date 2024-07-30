package ru.nak.ied.regist.entities

data class LogAgv(
    val id: Int?,
    val typeLog: String,
    val tabelNum: String,
    val timeOpenApp: String,
    val serialNumberAgv: String,
    val nameTO: String,
    val timeToAgv: String
)
