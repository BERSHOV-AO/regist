package ru.nak.ied.regist.db

import androidx.room.RoomDatabase

abstract class MainDataBase : RoomDatabase(){

    abstract fun getDao() : Dao
}