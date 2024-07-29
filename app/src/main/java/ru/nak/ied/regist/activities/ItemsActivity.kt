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
import ru.nak.ied.regist.api.MainApi
import ru.nak.ied.regist.databinding.ActivityItemsBinding
import ru.nak.ied.regist.entities.AGVItem
import ru.nak.ied.regist.entities.NameTO
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
    lateinit var listTOAgvNoTO: List<NameTO>;

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
        }

        binding.bToAgv.setOnClickListener {

            CoroutineScope(Dispatchers.Main).launch {

                listTOAgvNoTO = mainApi.getTOAgvBySNAndStatus(responseValueSerialNum.toString())

                Log.d("MyLog", "size: ${listTOAgvNoTO.size}")
                Log.d("MyLog", "sn: ${listTOAgvNoTO}")

                if (listTOAgvNoTO.size == 0) {
                    Toast.makeText(
                        this@ItemsActivity,
                        "У AGV sn: $responseValueSerialNum, все ТО выпонены!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val intent = Intent(this@ItemsActivity, MakeChangeAGVTOActivity::class.java)
                    intent.putExtra("agvSerialNumTo", responseValueSerialNum)
                    startActivity(intent)
                }
            }
        }
    }
}

