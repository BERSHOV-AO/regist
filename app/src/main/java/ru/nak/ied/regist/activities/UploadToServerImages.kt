package ru.nak.ied.regist.activities

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import ru.nak.ied.regist.R
import java.io.ByteArrayOutputStream

class UploadToServerImages {

    fun bitmapToBase64(context: Context): String {
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.delorean)
        val byteOutputStream = ByteArrayOutputStream()
        // выбираем формат нашего изображения
        // 100 - качество
        // поток в который мы это все посылаем
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteOutputStream)
        // теперь из потока превращяем все это в массив из байтов
        val byteArray = byteOutputStream.toByteArray()
        // Base64 --> android.utils
        // обязательно Base64.DEFAULT
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
}