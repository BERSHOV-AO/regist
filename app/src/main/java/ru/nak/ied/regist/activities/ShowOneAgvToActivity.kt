package ru.nak.ied.regist.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.nak.ied.regist.adapter.AGVTOAdapter
import ru.nak.ied.regist.api.MainApi
import ru.nak.ied.regist.databinding.ActivityShowOneAgvToBinding
import ru.nak.ied.regist.entities.NameTO
import javax.inject.Inject

@AndroidEntryPoint
class ShowOneAgvToActivity : AppCompatActivity() {

    @Inject
    lateinit var mainApi: MainApi
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AGVTOAdapter

    var listTOAgv: List<NameTO>? = null
    lateinit var listTOAgvNoTO: List<NameTO>

    private lateinit var binding: ActivityShowOneAgvToBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShowOneAgvToBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.rcViewAgvToShow
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Изначальная загрузка данных
        loadData()

        binding.bToOneAgv.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val responseSerialNum = intent.getStringExtra("SERIAL_NUMBER").toString()
                binding.tvSerNumShow.text = responseSerialNum
                listTOAgvNoTO = mainApi.getTOAgvBySNAndStatus(responseSerialNum)

                if (listTOAgvNoTO.isEmpty()) {
                    Toast.makeText(
                        this@ShowOneAgvToActivity,
                        "У AGV sn: $responseSerialNum, все ТО выполнены!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val intent =
                        Intent(this@ShowOneAgvToActivity, MakeChangeAGVTOActivity::class.java)
                    intent.putExtra("agvSerialNumTo", responseSerialNum)
                    startActivity(intent)
                }
            }
        }
    }

    // Метод для загрузки данных в RecyclerView
    private fun loadData() {
        val responseSerialNum = intent.getStringExtra("SERIAL_NUMBER")
        binding.tvSerNumShow.text = responseSerialNum
        CoroutineScope(Dispatchers.Main).launch {
            listTOAgv = mainApi.getTOAgvBySN(responseSerialNum!!)
            adapter = AGVTOAdapter(this@ShowOneAgvToActivity, listTOAgv!!)
            recyclerView.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        // Обновление данных при возвращении в активити
        loadData()
    }
}