package ru.nak.ied.regist.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.nak.ied.regist.R
import ru.nak.ied.regist.api.MainApi
import ru.nak.ied.regist.databinding.ActivityItemsBinding
import ru.nak.ied.regist.entities.AGVItem
import ru.nak.ied.regist.entities.AgvDiagnostic
import ru.nak.ied.regist.entities.NameTO
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class ItemsActivity : AppCompatActivity() {

    @Inject
    lateinit var mainApi: MainApi

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AGVTOAdapter

    private lateinit var binding: ActivityItemsBinding

    var listAgv: List<AGVItem>? = null;
    var listTOAgv: List<NameTO>? = null;
    var agvSerialNum: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.rcViewAgvTo
        recyclerView.layoutManager = LinearLayoutManager(this)

        val responseValueSerialNum = intent.getStringExtra("key")

        binding.tvSnAgvText.text = responseValueSerialNum

        CoroutineScope(Dispatchers.Main).launch {
            listAgv = mainApi.getAllAGV()

            listTOAgv = mainApi.getTOAgvBySN(responseValueSerialNum!!)

            adapter = AGVTOAdapter(this@ItemsActivity, listTOAgv!!)
            recyclerView.adapter = adapter


            var agvFound = false
            listAgv?.forEach { agv ->
                if (agv.serialNumber == responseValueSerialNum) {
                    Log.d("MyLog", "AGV найден: ${agv.serialNumber}")
                    Toast.makeText(
                        this@ItemsActivity,
                        "AGV найден: ${agv.serialNumber}",
                        Toast.LENGTH_SHORT
                    ).show()

                    agvSerialNum = agv.serialNumber

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

        binding.bDiagnosticAgv.setOnClickListener {
            val intent = Intent(this, MakeAGVTOActivity::class.java)
            intent.putExtra("agvSerialNumTo", agvSerialNum)
            startActivity(intent)
        }
    }

    fun convertTime(timeString: String): String {
        if (timeString.isEmpty()) {
            return "Некорректное время"
        }

        val milliseconds = timeString.toLongOrNull() ?: 0
        if (milliseconds == 0L) {
            return "Некорректное время"
        }

        val date = Date(milliseconds)
        val outputFormat = SimpleDateFormat("hh:mm:ss - yyyy/MM/dd", Locale.getDefault())
        return outputFormat.format(date)
    }
}
