package ru.nak.ied.regist.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.nak.ied.regist.activities.App.Companion.baseUrl
import ru.nak.ied.regist.activities.BaseUrl
import ru.nak.ied.regist.api.MainApi
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun providesBaseUrl(): String {
        return baseUrl


      //  "http://192.168.0.7/host/"
    }

    @Provides
    @Singleton
    fun providesRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providesMainApi(retrofit: Retrofit): MainApi {
        return retrofit.create(MainApi::class.java)
    }
}

//******************************work******************************************
//@Module
//@InstallIn(SingletonComponent::class)
//object Module {
//    @Provides
//    @Singleton
//    fun providesRetrofit(): Retrofit {
//
//        return Retrofit.Builder()
//            .baseUrl("http://192.168.0.7/host/")
//            // .baseUrl("http://192.168.175.152/host/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//
//    @Provides
//    @Singleton
//    fun providesMainApi(retrofit: Retrofit): MainApi {
//        return retrofit.create(MainApi::class.java)
//    }
//}
//******************************************************************************




