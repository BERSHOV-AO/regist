package ru.nak.ied.regist.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.nak.ied.regist.R
import ru.nak.ied.regist.api.MainApi
import ru.nak.ied.regist.databinding.ActivityAuthBinding
import ru.nak.ied.regist.entities.User
import ru.nak.ied.regist.utils.TOData
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    @Inject
    lateinit var mainApi: MainApi

    private lateinit var binding: ActivityAuthBinding

    var list: List<User>? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //**********************************add TOData**********************************************
        TOData.addToMap("Корпус - очистка от загрязнений", "30")
        TOData.addToMap("Корпус - контроль резьбовых соединений, протяжка", "30")

        TOData.addToMap("Позиционирующие ролики - проверить на наличие люфтов", "30")
        TOData.addToMap("Разъем АКБ - проверка целостности, протяжка", "30")
        TOData.addToMap("Замки - наличие и срабатывание", "30")

        TOData.addToMap("Сканер безопасности - очистка сканера безопасности", "30")
        TOData.addToMap("Сканер безопасности - проверка крепления кабеля сканера", "30")

        TOData.addToMap("Бампер - контроль резьбовых соединений, протяжка", "30")
        TOData.addToMap("Лицевая панель - контроль целостности защиты панели", "30")

        TOData.addToMap("Электрика - проверка работы звука", "30")
        TOData.addToMap("Электрика - Контроль целостности кнопок и панели", "30")

        TOData.addToMap("Очистка (c разборкой)", "90")
        TOData.addToMap("Контроль резбовых соединений, протяжка", "30")
        TOData.addToMap("Осмотр, контроль люфтов", "30")
        TOData.addToMap("Проверить укладку проводов", "30")
        TOData.addToMap("Проверить работу", "30")
        TOData.addToMap("Контроль верхнего положения", "30")
        TOData.addToMap("Смазка", "30")

        TOData.addToMap("Подъемник - Проверить устан. штифты на кулачке", "30")
        TOData.addToMap("Подъемник - Контроль резбовых соединений, протяжка", "30")
        TOData.addToMap("Подъемник - Проверить крепление опорных подшипников", "30")
        TOData.addToMap("Подъемник - Проверить работу подъемника", "30")
        TOData.addToMap("Подъемник - Проверка срабат. концевиков (настройка)", "30")

        TOData.addToMap("Drive unit - Проверить крепление поъемной втулки", "30")
        TOData.addToMap("Drive unit - Контроль резбовых соединений, протяжка", "30")
        TOData.addToMap("Drive unit - Проверка укладки и целостности кабеля", "30")
        TOData.addToMap("Drive unit - Осмотр контроль люфтов", "30")
        TOData.addToMap("Drive unit - Контроль натяжения цепей", "90")
        TOData.addToMap("Drive unit - Смазка цепей", "30")

        TOData.addToMap("Датчик трека и RFID - Проверить крепление", "30")
        TOData.addToMap("Датчик трека и RFID - Проверить целостность", "30")
        TOData.addToMap("Датчик трека и RFID - Проверить целостность и укладку кабелей", "30")
        TOData.addToMap("Датчик трека и RFID - Проверить крепление и наличие защиты", "30")
        TOData.addToMap("Датчик трека и RFID - Высота от пола", "30")

        TOData.addToMap("Колеса приводные - Контроль диаметра наружного", "30")
        TOData.addToMap("Колеса приводные - Проверить продольный люфт", "30")
        TOData.addToMap("Колеса приводные - Проверить крепление крышки колеса", "30")

        TOData.addToMap("Приводные звездочки - Очистка от мусора", "30")
        TOData.addToMap("Приводные звездочки - Проверить устан. штифты на звездах", "30")
        TOData.addToMap("Приводные звездочки - Проверить крепление крышки звездочки", "30")

        TOData.addToMap("Колеса поворотные - Очистка от загрязнений", "30")
        TOData.addToMap("Колеса поворотные - Проверить продольный люфт", "30")

        TOData.addToMap("Колеса задние - Проверить продольный люфт", "30")
        //******************************************************************************************

        val context = this@AuthActivity

        CoroutineScope(Dispatchers.Main).launch {
            val isConnected = getDataBaseConnection()

            if (isConnected) {
                binding.imWifi.setImageResource(R.drawable.ic_wifi_green)
            } else {
                binding.imWifi.setImageResource(R.drawable.ic_wifi_red)
            }
        }

        binding.imWifi.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val isConnected = getDataBaseConnection()
                if (isConnected) {
                    binding.imWifi.setImageResource(R.drawable.ic_wifi_green)
                    Toast.makeText(
                        context, "Связь с сервером установлена!",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    binding.imWifi.setImageResource(R.drawable.ic_wifi_red)
                    Toast.makeText(
                        context, "Нет связи с сервером, проверьте wifi соединение, ip адрес!",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }
        }

        binding.linkToReg.setOnClickListener {

            CoroutineScope(Dispatchers.Main).launch {
                val isConnected = getDataBaseConnection()

                if (isConnected) {
                    binding.imWifi.setImageResource(R.drawable.ic_wifi_green)
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    binding.imWifi.setImageResource(R.drawable.ic_wifi_red)
                    Toast.makeText(
                        context, "Нет связи с сервером, проверьте wifi соединение, ip адрес!",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        binding.butSetting.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }


        binding.buttonAuth.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val isConnected = getDataBaseConnection()
                if (isConnected) {
                    binding.imWifi.setImageResource(R.drawable.ic_wifi_green)
                    val login = binding.userLoginAuth.text.toString().trim()
                    val pass = binding.userPussAuth.text.toString().trim()

                    if (login == "" || pass == "") {
                        Toast.makeText(
                            context, "Не все поля заполнены",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        try {
                            val list = withContext(Dispatchers.IO) {
                                mainApi.getAllUsers()
                            }

                            var userFound = false
                            list?.forEach { user ->
                                if (user.login == login && user.pass == pass) {
                                    Log.d("MyLog", "Пользователь найден: $user")
                                    binding.userLoginAuth.text.clear()
                                    binding.userPussAuth.text.clear()

                                    val intent = Intent(context, UserActivity::class.java)
                                    intent.putExtra("login", login)
                                    startActivity(intent)

                                    Toast.makeText(
                                        context,
                                        "Пользователь с табельным номером $login авторизован",
                                        Toast.LENGTH_LONG
                                    ).show()

                                    userFound = true
                                    return@launch // Выход из функции launch
                                }
                            }

                            if (!userFound) {
                                Toast.makeText(
                                    context,
                                    "Пользователь с табельным номером $login НЕ авторизован",
                                    Toast.LENGTH_LONG
                                ).show()
                                Log.d("MyLog", "Пользователь не найден")
                            }
                        } catch (e: Exception) {
                            Log.e(
                                "MyLog",
                                "Ошибка при получении списка пользователей: ${e.message}"
                            )
                        }
                    }
                } else {
                    binding.imWifi.setImageResource(R.drawable.ic_wifi_red)
                    Toast.makeText(
                        context, "Нет связи с сервером, проверьте " +
                                "wifi соединение, ip адрес!",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d("MyLog", "Нет связи с базой данных!")
                }
            }
        }
    }

    private suspend fun getDataBaseConnection(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val connectDB = mainApi.getConnectDB()
                Log.d("MyLog", "connect OK")
                connectDB
            } catch (e: Exception) {
                Log.d("MyLog", "connect error")
                e.printStackTrace()
                false
            }
        }
    }
}

//        binding.buttonAuth.setOnClickListener {
//            CoroutineScope(Dispatchers.Main).launch {
//                val isConnected = getDataBaseConnection()
//                if (isConnected) {
//                    binding.imWifi.setImageResource(R.drawable.ic_wifi_green)
//                    val login = binding.userLoginAuth.text.toString().trim()
//                    val pass = binding.userPussAuth.text.toString().trim()
//
//                    if (login == "" || pass == "") {
//                        Toast.makeText(context, "Не все поля заполнены",
//                            Toast.LENGTH_LONG).show()
//                    } else {
//                        CoroutineScope(Dispatchers.Main).launch {
//                            list = mainApi.getAllUsers()
//
//                            var userFound =
//                                false // Булева переменная для отслеживания нахождения пользователя
//                            list?.forEach { user ->
//                                if (user.login == login && user.pass == pass) {
//
//                                    // Пользователь найден
//                                    Log.d("MyLog", "Пользователь найден: $user")
//                                    binding.userLoginAuth.text.clear()
//                                    binding.userPussAuth.text.clear()
//
//                                    val intent = Intent(context, UserActivity::class.java)
//                                    intent.putExtra("login", login)
//                                    startActivity(intent)
//
//                                    Toast.makeText(context, "Пользователь с табельным номером $login авторизован",
//                                        Toast.LENGTH_LONG).show()
//
//                                    userFound = true // Устанавливаем флаг, что пользователь найден
//                                    return@forEach // Выходим из цикла forEach
//                                }
//                            }
//                            if (!userFound) {
//                                Log.d("MyLog", "Пользователь не найден")
//                            }
//                        }
//                    }
//                } else {
//                    binding.imWifi.setImageResource(R.drawable.ic_wifi_red)
//                    Toast.makeText(context, "Нет связи с базой данных!",
//                        Toast.LENGTH_LONG).show()
//                    Log.d("MyLog", "Нет связи с базой данных!")
//                }
//            }
//        }
//    }

//private suspend fun getDataBaseConnection(): Boolean {
//    return withContext(Dispatchers.IO) {
//        try {
//            val connectDB = mainApi.getConnectDB()
//            Log.d("MyLog", "connect OK")
//            connectDB
//        } catch (e: Exception) {
//            Log.d("MyLog", "connect error")
//            e.printStackTrace()
//            false
//        }
//    }
//}
//}

//**************************************************************************************************
//            val exists = false
////            var list: List<User>? = null
//
//            val login = userLogin.text.toString().trim()
//            val pass = userPass.text.toString().trim()
//
//            if (login == "" || pass == "") {
//                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
//            } else {
////                val db = DbHelper(this, null)
////                val isAuth = db.getUser(login, pass)
//
//                CoroutineScope(Dispatchers.IO).launch {
//                    list = mainViewModel.getAllUsers()
//                }
//
//                Log.d("MyLog", "list: $list")
//
//
//
//                list?.forEach {
//                    if (it.login == login && it.pass == pass) {
//                        Toast.makeText(
//                            this,
//                            "Пользователь с табельным номером $login авторизован",
//                            Toast.LENGTH_LONG
//                        )
//                            .show()
//                        userLogin.text.clear()
//                        userPass.text.clear()
//
////                        val intent = Intent(this, UserActivity::class.java)
////                        intent.putExtra("login", login)
////                        startActivity(intent)
//                    }
////                if (isAuth) {
////
////
//                    else {
//                        Toast.makeText(
//                            this,
//                            "Пользователь с табельным номером $login НЕ авторизован",
//                            Toast.LENGTH_LONG
//                        )
//                            .show()
//                    }
//                }
//            }
//        }
//    }
//}