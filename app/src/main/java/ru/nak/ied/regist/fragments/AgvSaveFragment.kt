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
        val buttonOpenMenuDeleteAgv = view.findViewById<Button>(R.id.bMenuDeleteAgv)
        val listSerialNumAgvTv = view.findViewById<TextView>(R.id.tvListSerialNumAgv)
        val LayoutMenuDelete = view.findViewById<LinearLayout>(R.id.llMenuDelete)

        val buttonDeleteAgv = view.findViewById<Button>(R.id.bDeleteAgvBySerialNum)
        val serialNumDeleteET = view.findViewById<EditText>(R.id.etSerialNumAgvDelete)


        buttonSaveAgv.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_password, null)
            val etDialogPassword = dialogView.findViewById<EditText>(R.id.etDialogPassword)

            AlertDialog.Builder(requireContext())
                .setTitle("Введите пароль")
                .setView(dialogView)
                .setPositiveButton("OK") { dialog, _ ->
                    val enteredPassword = etDialogPassword.text.toString()
                    if (enteredPassword == "121286") {
                        Toast.makeText(context, "Пароль верный", Toast.LENGTH_SHORT).show()

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

        buttonOpenMenuDeleteAgv.setOnClickListener {
            LayoutMenuDelete.setVisibility(View.VISIBLE)
            CoroutineScope(Dispatchers.Main).launch {

                val listAgv = mainApi.getAllAGV()

                val serialNumbersList: List<String> = listAgv.map { it.serialNumber }
                val displayedText = serialNumbersList.joinToString(separator = "\n")
                listSerialNumAgvTv.text = displayedText
            }
        }

        buttonDeleteAgv.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val listAgv = mainApi.getAllAGV()

                val sn = serialNumDeleteET.text.toString().trim()

                if (sn.isEmpty()) {
                    Toast.makeText(
                        context, "Поле незаполнено",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    // Проверка наличия AGV с указанным серийным номером
                    val agvToDelete = listAgv.find { it.serialNumber == sn }

                    if (agvToDelete != null) {
                        // Если AGV найден, выполняем удаление
                        try {
                            mainApi.deleteAgvBySerialNumber(sn) // Предполагается, что deleteAGV - метод API для удаления
                            mainApi.deleteAgvTOBySerialNumber(sn) //

                            Toast.makeText(
                                context, "AGV с серийным номером $sn успешно удален",
                                Toast.LENGTH_LONG
                            ).show()

                        } catch (e: Exception) {

                            Log.d("MyLog", "error:  ${e.message}")
                            Toast.makeText(
                                context, "Ошибка sn: $sn : ${e.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        // Если AGV не найден
                        Toast.makeText(
                            context, "AGV с серийным номером $sn не найден",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
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