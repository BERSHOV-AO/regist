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
import ru.nak.ied.regist.adapter.LogAdapter
import ru.nak.ied.regist.api.MainApi
import ru.nak.ied.regist.databinding.ActivityShowLogBinding
import ru.nak.ied.regist.entities.LogAgv
import javax.inject.Inject

@AndroidEntryPoint
class ShowLogActivity : AppCompatActivity() {

    @Inject
    lateinit var mainApi: MainApi
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LogAdapter
    private lateinit var binding: ActivityShowLogBinding

    lateinit var listLog: List<LogAgv>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowLogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.rcViewLogShow
        recyclerView.layoutManager = LinearLayoutManager(this)

        CoroutineScope(Dispatchers.Main).launch {
            listLog = mainApi.getAllLog()
            Log.d("MyLog", "log  $listLog")
            val filteredList = listLog.filter { it.typeLog == "2" }
            adapter = LogAdapter(filteredList)
            recyclerView.adapter = adapter
        }
    }
}