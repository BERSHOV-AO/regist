package ru.nak.ied.regist.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import ru.nak.ied.regist.R
import ru.nak.ied.regist.databinding.ActivityUserBinding
import ru.nak.ied.regist.fragments.AgvAllFragment
import ru.nak.ied.regist.fragments.AgvSaveFragment
import ru.nak.ied.regist.fragments.FragmentManager

@AndroidEntryPoint
class UserActivity : AppCompatActivity() {
    lateinit var binding: ActivityUserBinding

    private var backPressedTime: Long = 0 // Время последнего нажатия кнопки "Назад"
    private lateinit var backToast: Toast // Toast для отображения предупреждения

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater) // активируем binding
        setContentView(binding.root)
        val receivedLogin = intent.getStringExtra("login")
        supportActionBar?.title = "Пользователь: $receivedLogin"

        //--------------------- Сохранение табельного номера в SharedPreferences-------------------
        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("tabel_number", receivedLogin)
        editor.apply()
        //-----------------------------------------------------------------------------------------

        setBottomNavListener()
        FragmentManager.setFragment(AgvAllFragment.newInstance(), this)

        binding.ibLogOpe.setOnClickListener {
            val intent = Intent(this, ShowLogActivity::class.java)
            startActivity(intent);
        }

        binding.ibAdminMenu.setOnClickListener {
            //-----------------------------------------pass-----------------------------------------
            val dialogView = layoutInflater.inflate(R.layout.dialog_password, null)
            val etDialogPassword = dialogView.findViewById<EditText>(R.id.etDialogPassword)
            AlertDialog.Builder(this@UserActivity)
                .setTitle("Для входа в меню администратора, введите пароль:")
                .setView(dialogView)
                .setPositiveButton("OK") { dialog, _ ->
                    val enteredPassword = etDialogPassword.text.toString()
                    if (enteredPassword == "красный") {
                        Toast.makeText(
                            this@UserActivity, "Пароль верный",
                            Toast.LENGTH_SHORT
                        ).show()

                        //--------------------------------------------------------------------------
                        val intent = Intent(this, AdminMenuActivity::class.java)
                        startActivity(intent);

                        //--------------------------------------pass--------------------------------
                    } else {
                        // Пароль неверный, показать сообщение об ошибке
                        Toast.makeText(
                            this@UserActivity, "Неверный пароль",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("Отмена") { dialog, _ ->
                    dialog.dismiss()
                }.show()
            //-------------------------------------------------------------------------------------
        }
    }

    private fun setBottomNavListener() {
        binding.bNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.addAGV -> {
                    Log.d("MyLog", "addAGV")
                    FragmentManager.setFragment(AgvSaveFragment.newInstance(), this)
                }

                R.id.list_agv -> {
                    Log.d("MyLog", "list_agv")
                    FragmentManager.setFragment(AgvAllFragment.newInstance(), this)
                }

                R.id.qrcode -> {
                    Log.d("MyLog", "qrcode")

                    val intent = Intent(this, ScannerActivity::class.java)
                    startActivity(intent);
                }
            }
            true
        }
    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel() // Отменяем предыдущий Toast
            super.onBackPressed() // Выходим из активности
            return
        } else {
            backToast = Toast.makeText(
                applicationContext,
                "Нажмите еще раз, чтобы выйти",
                Toast.LENGTH_SHORT
            )
            backToast.show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}
