package ru.nak.ied.regist.entities

data class NameTO(
    val id: Int?,
    val nameTo: String,
    val serialNumberAGV: String,
    val frequencyOfTo: String,
    var statusTo: Boolean,
    val dataTo: String
)
