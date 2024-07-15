package ru.nak.ied.regist.activities

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.nak.ied.regist.R
import java.io.BufferedReader
import java.io.InputStreamReader


@HiltAndroidApp
class App : Application() {

    companion object {
        lateinit var baseUrl: String
    }

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface BaseUrlProvider {
        fun baseUrl(): String
    }

    override fun onCreate() {
        super.onCreate()
        baseUrl = "http://".plus(readIpAddressFromFile(this)).plus("/host/")
    }
}

private fun readIpAddressFromFile(context: Context): String {
    try {
        val fileInputStream = context.openFileInput("ip_config.txt")
        val inputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        val stringBuilder = StringBuilder()
        var text: String?
        while (bufferedReader.readLine().also { text = it } != null) {
            stringBuilder.append(text)
        }
        return stringBuilder.toString()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return "192.168.0.7"
}


//************************************work****************************************
//@HiltAndroidApp()
//class App : Application() {
//}
//************************************work****************************************