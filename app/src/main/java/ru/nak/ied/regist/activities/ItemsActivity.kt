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
import ru.nak.ied.regist.R
import ru.nak.ied.regist.databinding.ActivityItemsBinding
import ru.nak.ied.regist.databinding.ActivityNewAgvBinding
import ru.nak.ied.regist.db.MainViewModel

class ItemsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityItemsBinding

    private val mainViewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory((application as MainApp).database)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val responseValueSerialNum = intent.getStringExtra("key");

        mainViewModel.allAGV.observe(this, Observer { items ->

            var isItemFound =
                false // Флаг для отслеживания наличия элемента с заданным серийным номером

            items.forEach { item ->
                if (responseValueSerialNum.equals(item.serialNumber)) {
                    binding.tvSerialNumber.text = item.serialNumber
                    binding.tvDescript.text = item.description
                    isItemFound = true
                }
            }
            if (!isItemFound) {
                Toast.makeText(this, "QR code в базе данных не найден!", Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }
}