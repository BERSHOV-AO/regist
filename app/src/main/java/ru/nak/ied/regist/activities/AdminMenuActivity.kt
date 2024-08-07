package ru.nak.ied.regist.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.nak.ied.regist.R
import ru.nak.ied.regist.api.MainApi
import ru.nak.ied.regist.databinding.ActivityAdminMenuBinding
import ru.nak.ied.regist.databinding.ActivityAuthBinding
import ru.nak.ied.regist.entities.ModelAGV
import javax.inject.Inject

@AndroidEntryPoint
class AdminMenuActivity : AppCompatActivity() {

    @Inject
    lateinit var mainApi: MainApi

    private lateinit var binding: ActivityAdminMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAdminMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bSaveModelAgv.setOnClickListener {
            val modelAgvText = binding.etModelAgv.text.toString().trim()
            if (modelAgvText.isEmpty()) {
                Toast.makeText(
                    this@AdminMenuActivity, "Не все поля заполнены",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                CoroutineScope(Dispatchers.Main).launch {
                    mainApi.saveModelAGV(ModelAGV(null, modelAgvText.toString()))
                }
                Toast.makeText(
                    this@AdminMenuActivity, "Model: $modelAgvText добавлена в базу данных",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}