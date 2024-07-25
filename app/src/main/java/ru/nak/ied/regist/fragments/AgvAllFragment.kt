package ru.nak.ied.regist.fragments

import android.app.AlertDialog
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.nak.ied.regist.R
import ru.nak.ied.regist.activities.AGVAdapter
import ru.nak.ied.regist.api.MainApi
import ru.nak.ied.regist.entities.AGVItem
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

        CoroutineScope(Dispatchers.Main).launch {
            val listAgv = mainApi.getAllAGV()
            Log.d("MyLog", "listAgv: $listAgv")

            agvList.addAll(listAgv)

            adapter = AGVAdapter(agvList) { serialNumber ->

                deleteAgv(serialNumber)
            }
            recyclerView.adapter = adapter
        }
        return view
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
}