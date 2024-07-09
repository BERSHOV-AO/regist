package ru.nak.ied.regist.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.nak.ied.regist.R
import ru.nak.ied.regist.databinding.ActivityItemsBinding
import ru.nak.ied.regist.databinding.ActivitySettingsBinding

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

                Toast.makeText(this, "ip address: $ipaddress", Toast.LENGTH_LONG).show()
            }
        }
    }
}