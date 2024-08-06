package ru.nak.ied.regist.activities

import android.annotation.SuppressLint
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
    lateinit var listTOAgvNoTO_0: List<NameTO>
    lateinit var listTOAgvNoTO_2: List<NameTO>

    private lateinit var binding: ActivityShowOneAgvToBinding

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShowOneAgvToBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.rcViewAgvToShow
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Изначальная загрузка данных
        loadData()


//        for (agv in listAgv) {
//            val listTOAgvNoTO = mainApi.getTOAgvBySNAndStatus_0(agv.serialNumber)
//            if (listTOAgvNoTO.isEmpty()) {
//                val listTOAgvNoTO2 = mainApi.getTOAgvBySNAndStatus_2(agv.serialNumber)
//                if(listTOAgvNoTO2.isEmpty()){
//                    agv.statusReadyTo = "1"
//                } else {
//                    agv.statusReadyTo = "2"
//                }
//            } else {
//                agv.statusReadyTo = "0"
//            }

        binding.bToOneAgv.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val responseSerialNum = intent.getStringExtra("SERIAL_NUMBER").toString()
                binding.tvSerNumShow.text = responseSerialNum
                listTOAgvNoTO_0 = mainApi.getTOAgvBySNAndStatus_0(responseSerialNum)
                listTOAgvNoTO_2 = mainApi.getTOAgvBySNAndStatus_2(responseSerialNum)

                if (listTOAgvNoTO_0.isEmpty() && listTOAgvNoTO_2.isEmpty()) {

                    Toast.makeText(
                        this@ShowOneAgvToActivity,
                        "У AGV sn: $responseSerialNum, все ТО выполнены!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                if (listTOAgvNoTO_0.isNotEmpty()) {
                    val intent =
                        Intent(this@ShowOneAgvToActivity, MakeChangeAGVTOActivity::class.java)
                    intent.putExtra("agvSerialNumTo", responseSerialNum)
                    intent.putExtra("keyStatusTo", "0")
                    startActivity(intent)
                }

                if (listTOAgvNoTO_2.isNotEmpty()) {
                    val intent =
                        Intent(this@ShowOneAgvToActivity, MakeChangeAGVTOActivity::class.java)
                    intent.putExtra("agvSerialNumTo", responseSerialNum)
                    intent.putExtra("keyStatusTo", "2")
                    startActivity(intent)
                }
//                } else {
//                    val intent =
//                        Intent(this@ShowOneAgvToActivity, MakeChangeAGVTOActivity::class.java)
//                    intent.putExtra("agvSerialNumTo", responseSerialNum)
//                    startActivity(intent)
//                }
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