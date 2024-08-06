package ru.nak.ied.regist.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.nak.ied.regist.R
import ru.nak.ied.regist.adapter.AGVAdapter
import ru.nak.ied.regist.activities.ShowOneAgvToActivity
import ru.nak.ied.regist.api.MainApi
import ru.nak.ied.regist.entities.AGVItem
import ru.nak.ied.regist.entities.NameTO
import javax.inject.Inject

@AndroidEntryPoint
class AgvAllFragment : BaseFragment() {

    @Inject
    lateinit var mainApi: MainApi
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AGVAdapter
    private var agvList: MutableList<AGVItem> = mutableListOf() // Используем MutableList


    override fun onClickNew() {
        TODO("Not yet implemented")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_agv, container, false)

        recyclerView = view.findViewById(R.id.rcVewNote)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        loadAgvData() // Загрузка данных при создании фрагмента
        return view
    }

    private fun openAgvToListActivity(serialNumber: String) {
        val intent = Intent(requireContext(), ShowOneAgvToActivity::class.java)
        intent.putExtra("SERIAL_NUMBER", serialNumber)
        startActivity(intent)
    }

    private fun deleteAgv(serialNumber: String) {

        val dialogView = layoutInflater.inflate(R.layout.dialog_password, null)
        val etDialogPassword = dialogView.findViewById<EditText>(R.id.etDialogPassword)

        AlertDialog.Builder(requireContext())
            .setTitle(
                "Для удаления AGV c S/N: $serialNumber, \n" +
                        "введите пароль! "
            )
            .setView(dialogView)
            .setPositiveButton("OK") { dialog, _ ->
                val enteredPassword = etDialogPassword.text.toString()
                if (enteredPassword == "121286") {
                    Toast.makeText(context, "Пароль верный", Toast.LENGTH_SHORT).show()

                    CoroutineScope(Dispatchers.Main).launch {

                        mainApi.deleteAgvBySerialNumber(serialNumber)
                        mainApi.deleteAgvTOBySerialNumber(serialNumber)

                        // Здесь вы можете добавить код для удаления AGV из базы данных или API.
                        // После успешного удаления обновите список в адаптере:
                        adapter.removeItem(serialNumber)
                    }
                } else {
                    // Пароль неверный, показать сообщение об ошибке
                    Toast.makeText(context, "Неверный пароль", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Отмена") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = AgvAllFragment()
    }

    override fun onResume() {
        super.onResume()
        loadAgvData() // Обновляем данные при возвращении к фрагменту
    }

//    private fun loadAgvData() {
//        CoroutineScope(Dispatchers.Main).launch {
//            val listAgv = mainApi.getAllAGV()
//
//            for (agv in listAgv) {
//               // val listTOAgvNoTO = mainApi.getTOAgvBySNAndStatus_0(agv.serialNumber)
//                val listTOAgvNoTO = loadAgvData0(agv)
//                if (listTOAgvNoTO.isEmpty()) {
//                   // val listTOAgvNoTO2 = mainApi.getTOAgvBySNAndStatus_2(agv.serialNumber)
//                    val listTOAgvNoTO2 = loadAgvData2(agv)
//                    if(listTOAgvNoTO2.isEmpty()){
//                        agv.statusReadyTo = "1"
//                    } else {
//                        agv.statusReadyTo = "2"
//                    }
//                } else {
//                    agv.statusReadyTo = "0"
//                }
//
//                Log.d("MyLog", "listAgv: $listAgv")
//
//                agvList.clear() // Очистите список перед добавлением новых данных
//                agvList.addAll(listAgv)
//
//                if (!::adapter.isInitialized) {
//                    adapter = AGVAdapter(agvList, { serialNumber ->
//                        deleteAgv(serialNumber)
//                    }, { serialNumber ->
//                        openAgvToListActivity(serialNumber)
//                    })
//                    recyclerView.adapter = adapter
//                } else {
//                    adapter.notifyDataSetChanged() // Обновите адаптер, если он уже инициализирован
//                }
//            }
//        }
//    }


    private fun loadAgvData() {
        CoroutineScope(Dispatchers.Main).launch {
            val listAgv = mainApi.getAllAGV()

            // Создаем список Deferred для хранения результатов
            val deferredResults = mutableListOf<Deferred<AGVItem>>() // Измените тип на AGVItem

            for (agv in listAgv) {
                // Используем async для параллельного выполнения
                val deferred = async(Dispatchers.IO) {
                    val listTOAgvNoTO = loadAgvData0(agv)
                    val statusReadyTo = when {
                        listTOAgvNoTO.isEmpty() -> {
                            val listTOAgvNoTO2 = loadAgvData2(agv)
                            if (listTOAgvNoTO2.isEmpty()) "1" else "2"
                        }
                        else -> "0"
                    }
                    agv.statusReadyTo = statusReadyTo
                    agv // Возвращаем AGVItem с обновленным статусом
                }
                deferredResults.add(deferred)
            }

            // Ждем завершения всех корутин и собираем результаты
            val updatedAgvs = deferredResults.awaitAll() // Теперь это будет List<AGVItem>

            // Обновляем список и адаптер
            agvList.clear()
            agvList.addAll(updatedAgvs)

            if (!::adapter.isInitialized) {
                adapter = AGVAdapter(agvList, { serialNumber ->
                    deleteAgv(serialNumber)
                }, { serialNumber ->
                    openAgvToListActivity(serialNumber)
                })
                recyclerView.adapter = adapter
            } else {
                adapter.notifyDataSetChanged() // Обновите адаптер, если он уже инициализирован
            }

            Log.d("MyLog", "listAgv: $agvList")
        }
    }



    private suspend fun loadAgvData0(agv: AGVItem): List<NameTO> {
        return withContext(Dispatchers.IO) {
            mainApi.getTOAgvBySNAndStatus_0(agv.serialNumber)
        }
    }

    private suspend fun loadAgvData2(agv: AGVItem): List<NameTO> {
        return withContext(Dispatchers.IO) {
             mainApi.getTOAgvBySNAndStatus_2(agv.serialNumber)
        }
    }
}

