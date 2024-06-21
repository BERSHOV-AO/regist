package ru.nak.ied.regist.activities

import android.app.Application
import ru.nak.ied.regist.db.MainDataBase

class MainApp : Application() {
    val database by lazy { MainDataBase.getDataBase(this) }
}