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