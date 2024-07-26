//package ru.nak.ied.regist.activities
//
//import android.annotation.SuppressLint
//import android.os.Bundle
//import android.util.Log
//import android.widget.TextView
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import dagger.hilt.android.AndroidEntryPoint
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import ru.nak.ied.regist.R
//import ru.nak.ied.regist.api.MainApi
//import ru.nak.ied.regist.databinding.ActivityAgvAllBinding
//import ru.nak.ied.regist.databinding.ActivityAuthBinding
//import ru.nak.ied.regist.entities.AGVItem
//import javax.inject.Inject
//
//
//@AndroidEntryPoint
//class AgvAllActivity : AppCompatActivity() {
//
//    @Inject
//    lateinit var mainApi: MainApi
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var adapter: AGVAdapter
//    private var agvList: MutableList<AGVItem> = mutableListOf() // Используем MutableList
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_agv)
//
//        recyclerView = findViewById(R.id.rcVewNote)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        CoroutineScope(Dispatchers.Main).launch {
//            val listAgv = mainApi.getAllAGV()
//            Log.d("MyLog", "listAgv: $listAgv")
//
//            agvList.addAll(listAgv)
//
//            adapter = AGVAdapter(agvList){ serialNumber->
//
//                deleteAgv(serialNumber)
//            }
//            recyclerView.adapter = adapter
//        }
//    }
//
//    private fun deleteAgv(serialNumber: String) {
//
//        CoroutineScope(Dispatchers.Main).launch {
//
//
//            mainApi.deleteAgvBySerialNumber(serialNumber)
//            mainApi.deleteAgvTOBySerialNumber(serialNumber)
//
//            // Здесь вы можете добавить код для удаления AGV из базы данных или API.
//            // После успешного удаления обновите список в адаптере:
//            adapter.removeItem(serialNumber)
//        }
//    }
//}