package ru.nak.ied.regist.entities

data class ImageUploadResponse(
    // код/сообщение об отправке изображения
    val message: String,
    // ссылка на изображение
    val url: String
)
