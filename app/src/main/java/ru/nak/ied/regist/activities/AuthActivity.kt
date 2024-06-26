package ru.nak.ied.regist.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.nak.ied.regist.R
import ru.nak.ied.regist.db.DbHelper

class AuthActivity : AppCompatActivity() {
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

        bSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        button.setOnClickListener {

            val login = userLogin.text.toString().trim()
            val pass = userPass.text.toString().trim()

            if (login == "" || pass == "") {
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
            } else {
                val db = DbHelper(this, null)
                val isAuth = db.getUser(login, pass)

                if (isAuth) {
                    Toast.makeText(this, "Пользователь с табельным номером $login авторизован", Toast.LENGTH_LONG)
                        .show()
                    userLogin.text.clear()
                    userPass.text.clear()

//                    val intent = Intent(this, ItemsActivity::class.java)
//                    startActivity(intent)
                    val intent = Intent(this, UserActivity::class.java)
                    intent.putExtra("login", login)
                    startActivity(intent)

                } else {
                    Toast.makeText(this, "Пользователь с табельным номером $login НЕ авторизован", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }
}