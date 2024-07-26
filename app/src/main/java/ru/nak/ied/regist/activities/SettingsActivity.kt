package ru.nak.ied.regist.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.nak.ied.regist.R
import ru.nak.ied.regist.databinding.ActivityItemsBinding
import ru.nak.ied.regist.databinding.ActivitySettingsBinding
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.io.InputStreamReader

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    private var fileIp: String = "ip_config.txt"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bSetIP.setOnClickListener {
            val ip_one = binding.ipOne.text.toString().trim()
            val ip_second = binding.ipSecond.text.toString().trim()
            val ip_third = binding.ipThird.text.toString().trim()
            val ip_fourth = binding.ipFourth.text.toString().trim()

            if (ip_one == "" || ip_second == "" || ip_third == "" || ip_fourth == "") {
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_LONG).show()
            } else {
                val ipaddress: String =
                    ip_one.plus(".")
                        .plus(ip_second)
                        .plus(".")
                        .plus(ip_third)
                        .plus(".")
                        .plus(ip_fourth)

                saveIpAddressToFile(this, ipaddress)
                Toast.makeText(this, "Установлен ip address: $ipaddress", Toast.LENGTH_LONG).show()
                showRestartDialog(this)
                //restartApplication(this)
            }
        }

        binding.readIP.setOnClickListener {
            val ipAddress = readIpAddressFromFile(this)
            binding.textIP.text = ipAddress
        }
    }


    private fun saveIpAddressToFile(context: Context, ipAddress: String) {
        try {
            val fileOutputStream = context.openFileOutput(fileIp, Context.MODE_PRIVATE)
            fileOutputStream.write(ipAddress.toByteArray())
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun readIpAddressFromFile(context: Context): String {
        try {
            val fileInputStream = context.openFileInput(fileIp)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)
            val stringBuilder = StringBuilder()
            var text: String?
            while (bufferedReader.readLine().also { text = it } != null) {
                stringBuilder.append(text)
            }
            return stringBuilder.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "192.168.0.7"
    }

    fun restartApplication(context: Context) {
        val packageManager = context.packageManager
        val intent = packageManager.getLaunchIntentForPackage(context.packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        context.startActivity(intent)
        if (context is Activity) {
            (context as Activity).finish()
        }
    }

   fun showRestartDialog(context: Context) {
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setTitle("Предупреждение")
        alertDialogBuilder.setMessage("Приложение требует перезагрузки после смены IP!")

        alertDialogBuilder.setPositiveButton("Ok") { dialog, which ->
            // Действия при нажатии на кнопку "Да"
            // Здесь можно добавить код для перезагрузки приложения
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
       // Установка красного цвета фона для диалогового окна
       val dialogView = alertDialog.window?.decorView
       dialogView?.setBackgroundColor(context.resources.getColor(R.color.red_light))

       // Центрирование кнопки "Ok"
       val button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
       val layoutParams = button.layoutParams as android.widget.LinearLayout.LayoutParams
       layoutParams.gravity = Gravity.CENTER
       button.layoutParams = layoutParams

    }
}
//**********************************************************************************************
//    private fun writeDataToFile(ipAddress: String) {
//        try {
//            val file = File(filesDir, fileName) // Создание файла во внутреннем хранилище приложения
//            val fileWriter =
//                FileWriter(file, false) // Указываем параметр false для перезаписи файла
//            fileWriter.write(ipAddress) // Записываем переданный IP-адрес в файл
//            fileWriter.flush()
//            fileWriter.close()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//
//
//    private fun readDataFromFile(): String {
//        var data = ""
//        try {
//            val file = File(filesDir, fileName)
//            val fileReader = FileReader(file)
//            val bufferedReader = BufferedReader(fileReader)
//
//            var line: String?
//            while (bufferedReader.readLine().also { line = it } != null) {
//                data += line // Считываем содержимое файла построчно и добавляем к общей строке
//            }
//
//            bufferedReader.close()
//            fileReader.close()
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//
//        return data
//    }
//**********************************************************************************************
