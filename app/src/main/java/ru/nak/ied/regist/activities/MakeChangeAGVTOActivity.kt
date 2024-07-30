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
import ru.nak.ied.regist.databinding.ActivityMakeAgvtoBinding
import ru.nak.ied.regist.entities.LogAgv
import ru.nak.ied.regist.entities.NameTO
import javax.inject.Inject

@AndroidEntryPoint
class MakeChangeAGVTOActivity : AppCompatActivity() {

    @Inject
    lateinit var mainApi: MainApi

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AGVChangeStatusTOAdapter

    private lateinit var binding: ActivityMakeAgvtoBinding

    var listTOAgv: MutableList<NameTO>? = null;
    var listTOAgvAfterSwitch: MutableList<NameTO>? = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMakeAgvtoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.rcViewAgvToMake
        recyclerView.layoutManager = LinearLayoutManager(this)

        val responseSerialNum = intent.getStringExtra("agvSerialNumTo");

        binding.tvSerNum.text = responseSerialNum

        CoroutineScope(Dispatchers.Main).launch {

            listTOAgv = mainApi.getTOAgvBySNAndStatus(responseSerialNum!!) as MutableList<NameTO>

            Log.d("MyLog", "listTOAgv false:   $listTOAgv")

            adapter = AGVChangeStatusTOAdapter(listTOAgv!!) { position, isChecked, switchText ->

                listTOAgvAfterSwitch?.add(
                    NameTO(
                        listTOAgv!![position].id,
                        listTOAgv!![position].nameTo,
                        listTOAgv!![position].serialNumberAGV,
                        listTOAgv!![position].frequencyOfTo,
                        isChecked,
                        getCurrentTime()
                    )
                )

                Log.d(
                    "MyLog",
                    "Switch at position $position changed to $isChecked with text '$switchText'"
                )

                Log.d("MyLog", "list Switch: ${listTOAgv.toString()} ")
            }
            recyclerView.adapter = adapter
        }
        binding.bSaveDataToAgv.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                // Перебираем каждый элемент в списке listTOAgv
                for (nameTO in listTOAgvAfterSwitch!!) {
                    try {
                        // Обновляем элемент на сервере
                        mainApi.updateAgvTo(nameTO)

                        mainApi.saveLogAgv(
                            LogAgv(
                                null,
                                "2",
                                "556949",
                                "null",
                                nameTO.serialNumberAGV,
                                nameTO.nameTo,
                                getCurrentTime()
                            )
                        )
                        // adapter.removeItem(nameTO.serialNumberAGV, nameTO.positionListTo)
                        adapter.removeItem(nameTO.nameTo)

                        // Обработка успешного ответа
                        Log.d("MyLog", "Successfully updated: ${nameTO.nameTo}")
                    } catch (e: Exception) {
                        // Обработка ошибки
                        Log.d("MyLog", "Error updating ${nameTO.nameTo}: ${e.message}")
                    }
                }
                listTOAgvAfterSwitch!!.clear()
            }
        }
    }

    private fun getCurrentTime(): String {
        val currentTimeMillis = System.currentTimeMillis()
        return currentTimeMillis.toString()
    }
}