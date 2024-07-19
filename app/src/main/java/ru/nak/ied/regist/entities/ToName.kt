package ru.nak.ied.regist.entities

data class ToName(
    val id: Int?,
    val nameTo: String,
    val serialNumberAGV: String,
    val frequencyOfTo: String,
    val statusTo: Boolean,
    val dataTo: String
)
