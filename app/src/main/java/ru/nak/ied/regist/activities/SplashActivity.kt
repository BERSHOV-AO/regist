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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

/**
 * statusTo - 1 ---> ТО выполнено
 * statusTo - 0 ----> TO не выполнено
 * statusTo - 2 ----> Через ~ N дней нужно выполнить TO
 */

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var mainApi: MainApi
    private val SPLASH_DISPLAY_LENGTH = 2000 // Время задержки в миллисекундах (здесь 3 секунды)
    private val NUMBER_OF_DAYS_BEFORE_TO: String = "5"

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

                        val numberOfDaysBeforeTO = convertDaysToMillis(NUMBER_OF_DAYS_BEFORE_TO)
                        val dataAndFrequencyDays = dataToMillis + frequencyMillis
                        val dataAndFrequencyMinusDaysBeforeTO =
                            dataAndFrequencyDays - numberOfDaysBeforeTO

                        Log.d(
                            "MyLog",
                            "Текущее время: $currentTimeMillis, dataTo: $dataToMillis, frequencyMillis: $frequencyMillis"
                        )

                        if (currentTimeMillis > dataAndFrequencyDays) {
                            Log.d("MyLog", "!!!!!!!!!!!!!!!!!!!!!!!NOK statusTo")
                            nameTO.statusTo = "0"
                            mainApi.updateAgvTo(nameTO)
                        } else if (currentTimeMillis in dataAndFrequencyMinusDaysBeforeTO..dataAndFrequencyDays) {
                            Log.d("MyLog", "!!!!!!!!!!!!!!!!!!!!!!! statusBeforeTo")
                            nameTO.statusTo = "2"
                            mainApi.updateAgvTo(nameTO)
                        } else {
                            nameTO.statusTo = "1"
                            mainApi.updateAgvTo(nameTO)
                        }



                        //-----------------------------------------------------------------------

//                        Log.d(
//                            "MyLog",
//                            "Текущее время: $currentTimeMillis, dataTo: $dataToMillis, frequencyMillis: $frequencyMillis"
//                        )
//
//                        Log.d(
//                            "MyLog",
//                            "Текущее время: ${convertTime(currentTimeMillis.toString())}, время до TO: ${convertTime(dataAndFrequencyDays.toString())})"
//                        )
//
//                        //  if (currentTimeMillis > (dataToMillis + frequencyMillis)) {
//                        if (currentTimeMillis > dataAndFrequencyDays) {
//                            Log.d(
//                                "MyLog",
//                                "!!!!!!!!!!!!!!!!!!!!!!!OK statusTo"
//                            )
//                            //  if (nameTO.statusTo != false) {
//                            nameTO.statusTo = false
//                            nameTO.statusBeforeTo = false
//                            mainApi.updateAgvTo(nameTO)
//                            //      }
//                        }
//
//                        if(currentTimeMillis >= dataAndFrequencyMinusDaysBeforeTO && currentTimeMillis <= dataAndFrequencyDays) {
//                            Log.d(
//                                "MyLog",
//                                "!!!!!!!!!!!!!!!!!!!!!!!OK statusBeforeTo"
//                            )
//                            nameTO.statusTo = true
//                            nameTO.statusBeforeTo = true
//                            mainApi.updateAgvTo(nameTO)
//                        }
//

                        //----------------------------------------------------------------------

//                        if (currentTimeMillis > (dataToMillis + frequencyMillis)) {
//                            // if (nameTO.statusTo != false) {
//                            nameTO.statusTo = false
//                            mainApi.updateAgvTo(nameTO)
//                            // }
//                        }
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

fun convertTime(timeString: String): String {
    if (timeString.isEmpty()) {
        return "Некорректное время"
    }

    val milliseconds = timeString.toLongOrNull() ?: 0
    if (milliseconds == 0L) {
        return "Некорректное время"
    }

    val date = Date(milliseconds)
    val outputFormat = SimpleDateFormat("hh:mm:ss - yyyy/MM/dd", Locale.getDefault())
    return outputFormat.format(date)
}
