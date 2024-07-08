package ru.nak.ied.regist.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.nak.ied.regist.R
import ru.nak.ied.regist.api.MainApi
import ru.nak.ied.regist.api.MainViewModel
//import ru.nak.ied.regist.db.DbHelper
import ru.nak.ied.regist.entities.User
import javax.inject.Inject


@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    @Inject
    lateinit var mainApi: MainApi

//    @Inject
//    MainViewModel : ma

    @Inject
    lateinit var mainViewModel: MainViewModel

    var list: List<User>? = null;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)


        val bSettings: ImageButton = findViewById(R.id.butSetting)
        val userLogin: EditText = findViewById(R.id.user_login_auth)
        val userPass: EditText = findViewById(R.id.user_puss_auth)
        val button: Button = findViewById(R.id.button_auth)
        val linkToReg: TextView = findViewById(R.id.link_to_reg)

        linkToReg.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

//        bSettings.setOnClickListener {
//            startActivity(Intent(this, SettingsActivity::class.java))
//        }

//        CoroutineScope(Dispatchers.IO).launch {
//            list = mainApi.getAllUsers()
//        }

        button.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val pass = userPass.text.toString().trim()

            if (login == "" || pass == "") {
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    list = mainApi.getAllUsers()

                    var userFound = false // Булева переменная для отслеживания нахождения пользователя
                    list?.forEach { user ->
                        if (user.login == login && user.pass == pass) {
                            // Пользователь найден
                            Log.d("MyLog", "Пользователь найден: $user")
                            userLogin.text.clear()
                            userPass.text.clear()
                            userFound = true // Устанавливаем флаг, что пользователь найден
                            return@forEach // Выходим из цикла forEach
                        }
                    }

                    if (!userFound) {
                        // Если пользователь не найден, выполняем соответствующие действия
                        Log.d("MyLog", "Пользователь не найден")
                    }
                }
            }
        }

    }
}

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