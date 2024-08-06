package ru.nak.ied.regist.entities

data class NameTO(
    val id: Int?,
    val nameTo: String,
    val serialNumberAGV: String,
    val frequencyOfTo: String,
    var statusTo: String,
    val dataTo: String
)