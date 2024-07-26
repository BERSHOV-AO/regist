package ru.nak.ied.regist.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    var listTOAgv: List<NameTO>? = null;

    private lateinit var binding: ActivityShowOneAgvToBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShowOneAgvToBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.rcViewAgvToShow
        recyclerView.layoutManager = LinearLayoutManager(this)

        val responseSerialNum = intent.getStringExtra("SERIAL_NUMBER");

        binding.tvSerNumShow.text = responseSerialNum

        CoroutineScope(Dispatchers.Main).launch {

            listTOAgv = mainApi.getTOAgvBySN(responseSerialNum!!)

            Log.d("MyLog", "listTOAgv false:   $listTOAgv")
            adapter = AGVTOAdapter(this@ShowOneAgvToActivity, listTOAgv!!)
            recyclerView.adapter = adapter
        }
    }
}