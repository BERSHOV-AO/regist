package ru.nak.ied.regist.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject


@AndroidEntryPoint
class AgvSaveFragment : BaseFragment() {

    @Inject
    lateinit var mainApi: MainApi

    override fun onClickNew() {
        TODO("Not yet implemented")
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_save_agv, container, false)

        val nameAgv = view.findViewById<EditText>(R.id.etNameAgv)
        val serialNumAgv = view.findViewById<EditText>(R.id.etSerialNumAgv)
        val descriptionAGV = view.findViewById<EditText>(R.id.etDescriptionAGV)
        val buttonSaveAgv = view.findViewById<Button>(R.id.bSaveAgv)

        buttonSaveAgv.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_password, null)
            val etDialogPassword = dialogView.findViewById<EditText>(R.id.etDialogPassword)

            AlertDialog.Builder(requireContext())
                .setTitle("Введите пароль")
                .setView(dialogView)
                .setPositiveButton("OK") { dialog, _ ->
                    val enteredPassword = etDialogPassword.text.toString()
                    if (enteredPassword == "красный") {
                        Toast.makeText(context, "Пароль верный", Toast.LENGTH_SHORT).show()
                        /**
                         * **************************CoroutineScope*********************************
                         */
                        CoroutineScope(Dispatchers.Main).launch {
                            mainApi.saveAGV(
                                AGVItem(
                                    null,
                                    nameAgv.text.toString(),
                                    serialNumAgv.text.toString(),
                                    descriptionAGV.text.toString(),
                                    getCurrentTime()
                                )
                            )

                            Toast.makeText(
                                context,
                                "Добавлен AGV c S/N: ${serialNumAgv.text}",
                                Toast.LENGTH_LONG
                            ).show()

                            nameAgv.text.clear()
                            serialNumAgv.text.clear()
                            descriptionAGV.text.clear()
                            /**
                             * *********************************************************************
                             */
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
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = AgvSaveFragment()
    }

    // взятие текущего времени
    private fun getCurrentTime(): String {
        val currentTimeMillis = System.currentTimeMillis()
        return currentTimeMillis.toString()
    }
}

//@AndroidEntryPoint
//class AgvSaveFragment : BaseFragment() {
//
//    @Inject
//    lateinit var mainApi: MainApi
//
//    override fun onClickNew() {
//        TODO("Not yet implemented")
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_save_agv, container, false)
//
//        val nameAgv = view.findViewById<EditText>(R.id.etNameAgv)
//        val serialNumAgv = view.findViewById<EditText>(R.id.etSerialNumAgv)
//        val descriptionAGV = view.findViewById<EditText>(R.id.etDescriptionAGV)
//        val buttonSaveAgv = view.findViewById<Button>(R.id.bSaveAgv)
//
//        buttonSaveAgv.setOnClickListener {
//            CoroutineScope(Dispatchers.Main).launch {
//                mainApi.saveAGV(
//                    AGVItem(
//                        null,
//                        nameAgv.text.toString(),
//                        serialNumAgv.text.toString(),
//                        descriptionAGV.text.toString(),
//                        getCurrentTime()
//                    )
//                )
//
//                Toast.makeText(
//                    context,
//                    "Добавлен AGV c S/N: ${serialNumAgv.text}",
//                    Toast.LENGTH_LONG
//                ).show()
//
//                nameAgv.text.clear()
//                serialNumAgv.text.clear()
//                descriptionAGV.text.clear()
//            }
//        }
//        return view
//    }
//
//    companion object {
//        @JvmStatic
//        fun newInstance() = AgvSaveFragment()
//    }
//
//    // взятие текущего времени
//    private fun getCurrentTime(): String {
//        val currentTimeMillis = System.currentTimeMillis()
//        return currentTimeMillis.toString()
//    }
//}

