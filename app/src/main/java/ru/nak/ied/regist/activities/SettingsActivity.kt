package ru.nak.ied.regist.activities

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.nak.ied.regist.R
import ru.nak.ied.regist.databinding.ActivityItemsBinding
import ru.nak.ied.regist.databinding.ActivitySettingsBinding
import java.io.BufferedReader
import java.io.InputStreamReader

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
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
                val ipaddress : String =
                    ip_one.plus(".")
                    .plus(ip_second)
                    .plus(".")
                    .plus(ip_third)
                    .plus(".")
                    .plus(ip_fourth)

                saveIpAddressToFile(this,ipaddress)

                Toast.makeText(this, "ip address: $ipaddress", Toast.LENGTH_LONG).show()

            }
        }

        binding.readIP.setOnClickListener {
            val ipAddress = readIpAddressFromFile(this)
            binding.textIP.text = ipAddress
        }
    }

    // Функция для записи IP-адреса в файл
    private fun saveIpAddressToFile(context: Context, ipAddress: String) {
        try {
            val fileOutputStream = context.openFileOutput("ip_config.txt", Context.MODE_PRIVATE)
            fileOutputStream.write(ipAddress.toByteArray())
            fileOutputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    private fun readIpAddressFromFile(context: Context): String {
        try {
            val fileInputStream = context.openFileInput("ip_config.txt")
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
        return "default_ip_address"
    }

}