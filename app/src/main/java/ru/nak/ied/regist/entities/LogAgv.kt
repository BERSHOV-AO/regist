package ru.nak.ied.regist.entities

data class LogAgv(
    val id: Int?,
    val tabelNum: String,
    val timeOpenApp: String,
    val timeToAgv: String? = null
)
