package ru.nak.ied.regist.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.nak.ied.regist.R
import ru.nak.ied.regist.api.MainApi
import ru.nak.ied.regist.databinding.ActivityAgvAllBinding
import ru.nak.ied.regist.databinding.ActivityAuthBinding
import ru.nak.ied.regist.entities.AGVItem
import javax.inject.Inject

@AndroidEntryPoint
class AgvAllActivity : AppCompatActivity() {

    @Inject
    lateinit var mainApi: MainApi
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AGVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_agv)

        recyclerView = findViewById(R.id.rcVewNote)
        recyclerView.layoutManager = LinearLayoutManager(this)

        CoroutineScope(Dispatchers.Main).launch {
            val listAgv = mainApi.getAllAGV()
            Log.d("MyLog", "listAgv: $listAgv")

            adapter = AGVAdapter(listAgv)
            recyclerView.adapter = adapter
        }
    }
}