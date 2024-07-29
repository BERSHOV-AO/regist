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
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.nak.ied.regist.R
import ru.nak.ied.regist.api.MainApi
import ru.nak.ied.regist.entities.AGVItem
import ru.nak.ied.regist.entities.NameTO
import ru.nak.ied.regist.utils.TOData
import javax.inject.Inject


@AndroidEntryPoint
class AgvSaveFragment : BaseFragment() {

    @Inject
    lateinit var mainApi: MainApi

    var listAgv: List<AGVItem>? = null;

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
        val versionFW = view.findViewById<EditText>(R.id.etVersionFW)
        val modelAgv = view.findViewById<EditText>(R.id.etModelAgv)
        val ePlan = view.findViewById<EditText>(R.id.etePlan)
        val buttonSaveAgv = view.findViewById<Button>(R.id.bSaveAgv)

        buttonSaveAgv.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_password, null)
            val etDialogPassword = dialogView.findViewById<EditText>(R.id.etDialogPassword)

            //-----------------------------------------pass-----------------------------------------
            AlertDialog.Builder(requireContext())
                .setTitle("Введите пароль")
                .setView(dialogView)
                .setPositiveButton("OK") { dialog, _ ->
                    val enteredPassword = etDialogPassword.text.toString()
                    if (enteredPassword == "121286") {
                        Toast.makeText(context, "Пароль верный", Toast.LENGTH_SHORT).show()
                        //--------------------------------------------------------------------------

                        val thisCurrentTime: String = getCurrentTime()
                        /**
                         * **************************CoroutineScope*********************************
                         */
                        CoroutineScope(Dispatchers.Main).launch {
                            mainApi.saveAGV(
                                AGVItem(
                                    null,
                                    nameAgv.text.toString(),
                                    serialNumAgv.text.toString(),
                                    versionFW.text.toString(),
                                    modelAgv.text.toString(),
                                    ePlan.text.toString(),
                                    thisCurrentTime
                                )
                            )

                            for ((key, value) in TOData.toMap) {
                                println("Ключ: $key, Значение: $value")

                                mainApi.saveAgvTo(
                                    NameTO(
                                        null,
                                        key,
                                        serialNumAgv.text.toString(),
                                        value,
                                        true,
                                        thisCurrentTime
                                    )
                                )
                            }

                            Toast.makeText(
                                context,
                                "Добавлен AGV c S/N: ${serialNumAgv.text}",
                                Toast.LENGTH_LONG
                            ).show()

                            nameAgv.text.clear()
                            serialNumAgv.text.clear()
                            versionFW.text.clear()
                            modelAgv.text.clear()
                            ePlan.text.clear()
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