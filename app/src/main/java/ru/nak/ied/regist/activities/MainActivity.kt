package ru.nak.ied.regist.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.nak.ied.regist.R
import ru.nak.ied.regist.api.MainApi
import ru.nak.ied.regist.entities.User
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var mainApi: MainApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userName: EditText = findViewById(R.id.user_name)
        val userSurname: EditText = findViewById(R.id.user_surname)
        val userLogin: EditText = findViewById(R.id.user_login)
        val userPass: EditText = findViewById(R.id.user_puss)
        val button: Button = findViewById(R.id.button_reg)
        val linkToAuth: TextView = findViewById(R.id.link_to_auth)

        linkToAuth.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }

        button.setOnClickListener {

            val login = userLogin.text.toString().trim()
            val name = userName.text.toString().trim()
            val surname = userSurname.text.toString().trim()
            val pass = userPass.text.toString().trim()

            if (name.isEmpty() || surname.isEmpty() || login.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
            } else {
                val user = User(null, name, surname, login, pass)

                Toast.makeText(this, "Пользователь $login добавлен", Toast.LENGTH_LONG).show()

                userName.text.clear()
                userSurname.text.clear()
                userLogin.text.clear()
                userPass.text.clear()

                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        mainApi.saveUser(user)
                        Log.d("MyLog", "list: ${user.toString()}")
                    } catch (e: Exception) {
                        Log.d("MyLog", "list: ERROR")
                        // Обработка ошибок
                        e.printStackTrace()
                    }
                }
            }
        }
    }
}