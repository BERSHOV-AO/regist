package ru.nak.ied.regist.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.nak.ied.regist.R
import ru.nak.ied.regist.api.MainApi
import ru.nak.ied.regist.databinding.ActivityItemsBinding
import ru.nak.ied.regist.databinding.ActivityNewAgvBinding
import ru.nak.ied.regist.entities.AGVItem
import ru.nak.ied.regist.entities.User
import javax.inject.Inject

@AndroidEntryPoint
class ItemsActivity : AppCompatActivity() {

    @Inject
    lateinit var mainApi: MainApi

    private lateinit var binding: ActivityItemsBinding

    var listAgv: List<AGVItem>? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val responseValueSerialNum = intent.getStringExtra("key");


        CoroutineScope(Dispatchers.Main).launch {
            listAgv = mainApi.getAllAGV()

            var agvFound = false
            listAgv?.forEach { agv ->
                if (agv.serialNumber == responseValueSerialNum) {
                    Log.d("MyLog", "AGV найден: ${agv.serialNumber}")
                    Toast.makeText(
                        this@ItemsActivity,
                        "AGV найден: ${agv.serialNumber}",
                        Toast.LENGTH_SHORT
                    ).show()

                    binding.tvSerialNumber.text = agv.serialNumber
                    binding.tvDescript.text = agv.description
                    agvFound = true
                    return@launch // Выход из функции launch
                }
            }

            if (!agvFound) {
                Toast.makeText(
                    this@ItemsActivity,
                    "QR code в базе данных не найден!",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }
}
