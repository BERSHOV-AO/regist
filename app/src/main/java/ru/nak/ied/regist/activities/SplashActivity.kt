package ru.nak.ied.regist.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.nak.ied.regist.R
import ru.nak.ied.regist.api.MainApi
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var mainApi: MainApi
    private val SPLASH_DISPLAY_LENGTH = 2000 // Время задержки в миллисекундах (здесь 3 секунды)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Запускаем проверку AGV
        checkAGVStatus()
    }

    private fun checkAGVStatus() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                // Получаем список NameTO из базы данных
                val listAgvTO = mainApi.getAllAgvTo()

                val currentTimeMillis = System.currentTimeMillis()

                for (nameTO in listAgvTO) {
                    Log.d("MyLog", "$nameTO!")

                    if (nameTO.dataTo != null && nameTO.frequencyOfTo != null) {
                        val dataToMillis = nameTO.dataTo.toLong()
                        val frequencyMillis = convertDaysToMillis(nameTO.frequencyOfTo)

                        Log.d(
                            "MyLog",
                            "Текущее время: $currentTimeMillis, dataTo: $dataToMillis, frequencyMillis: $frequencyMillis"
                        )

                        if (currentTimeMillis > (dataToMillis + frequencyMillis)) {
                            // if (nameTO.statusTo != false) {
                            nameTO.statusTo = false
                            mainApi.updateAgvTo(nameTO)
                            // }
                        }
                    }
                }

                // После успешной проверки статуса, запускаем AuthActivity с задержкой
                withContext(Dispatchers.Main) {
                    delay(SPLASH_DISPLAY_LENGTH.toLong())
                    openAuthActivity()
                }

            } catch (e: Exception) {
                Log.e("MyLog", "Ошибка при получении данных из базы: ${e.message}")

                // Если произошла ошибка, открываем AuthActivity
                withContext(Dispatchers.Main) {
                    openAuthActivity()
                }
            }
        }
    }

    private fun openAuthActivity() {
        val mainIntent = Intent(this@SplashActivity, AuthActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

    fun convertDaysToMillis(days: String): Long {
        return try {
            val daysCount = days.toLong()
            daysCount * 24 * 60 * 60 * 1000
        } catch (e: NumberFormatException) {
            Log.e("MyLog", "Ошибка преобразования дней в миллисекунды: ${e.message}")
            0 // Возвращаем 0 или другое значение по умолчанию
        }
    }
}

//class SplashActivity : AppCompatActivity() {
//    private val SPLASH_DISPLAY_LENGTH = 3000 // Время задержки в миллисекундах (здесь 3 секунды)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splash)
//
//        Handler().postDelayed({
//            val mainIntent = Intent(this, AuthActivity::class.java)
//            startActivity(mainIntent)
//            finish()
//        }, SPLASH_DISPLAY_LENGTH.toLong())
//    }
//}