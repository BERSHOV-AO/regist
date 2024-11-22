package ru.nak.ied.regist.entities

data class User(
    val id: Int?,
    val name: String,
    val surname: String,
    val login: String,
    val pass: String
)