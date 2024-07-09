package ru.nak.ied.regist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.nak.ied.regist.api.MainApi
import java.io.File
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

//    private var ipAddress: String = readIpAddressFromFile()
//
//    private fun readIpAddressFromFile(): String {
//        val file = File("ip_config.txt")
//        return file.readText()
//    }

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {

        return Retrofit.Builder()
            .baseUrl("http://192.168.0.7/host/")
 //           .baseUrl("http://$ipAddress/host/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    fun providesMainApi(retrofit: Retrofit): MainApi {
        return retrofit.create(MainApi::class.java)
    }
}