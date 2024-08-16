package ru.nak.ied.regist.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.nak.ied.regist.R
import ru.nak.ied.regist.activities.UploadToServerImages
import ru.nak.ied.regist.api.MainApi
import ru.nak.ied.regist.entities.AGVItem
import ru.nak.ied.regist.entities.ImageData
import ru.nak.ied.regist.entities.NameTO
import javax.inject.Inject

/**
 * statusTo - 1 ---> ТО выполнено
 * statusTo - 0 ----> TO не выполнено
 * statusTo - 2 ----> Через ~ N дней нужно выполнить TO
 */

@AndroidEntryPoint
class AgvSaveFragment : BaseFragment() {

    @Inject
    lateinit var mainApi: MainApi

    lateinit var spinnerFW: Spinner
    lateinit var dirCompos: Array<String>

    lateinit var spinnerModelAgv: Spinner
    lateinit var agvModels: Array<String>

    lateinit var spinnerEPlan: Spinner
    lateinit var ePlanScheme: Array<String>

    val agv_1100_st: String = "AGV-1100-ST"
    val agv_1100_2p: String = "AGV-1100-2P"
    val agv_1100_2t: String = "AGV-1100-2T"
    val agv_3000_st: String = "AGV-3000-ST"

    override fun onClickNew() {
        TODO("Not yet implemented")
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_save_agv, container, false)

        val uploadToServerImages = UploadToServerImages()

        val nameAgv = view.findViewById<EditText>(R.id.etNameAgv)
        val serialNumAgv = view.findViewById<EditText>(R.id.etSerialNumAgv)
        spinnerFW = view.findViewById<Spinner>(R.id.spinnerVersionFW)
        spinnerModelAgv = view.findViewById<Spinner>(R.id.spinnerModelAgv)
        spinnerEPlan = view.findViewById<Spinner>(R.id.spinnerEPlan)
        val buttonSaveAgv = view.findViewById<Button>(R.id.bSaveAgv)
        val buttonSaveImage = view.findViewById<Button>(R.id.bSaveImage)

        loadModelAndInitAdapter()
        loadFWAndInitAdapter()
        loadEPlanSchemesAndInitAdapter()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = mainApi.getDir()
                // Обработка ответа
                Log.d("MyLog", "Directories: ${response.directories}")
                Log.d("MyLog", "Files: ${response.files}")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        buttonSaveAgv.setOnClickListener {
            //-----------------------------------------pass-----------------------------------------
            val dialogView = layoutInflater.inflate(R.layout.dialog_password, null)
            val etDialogPassword = dialogView.findViewById<EditText>(R.id.etDialogPassword)
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
                                    spinnerFW.selectedItem.toString(),
                                    spinnerModelAgv.selectedItem.toString(),
                                    spinnerEPlan.selectedItem.toString(),
                                    thisCurrentTime
                                )
                            )
                            //---------------------------agv_1100_st--------------------------------
                            //----------------------------------------------------------------------
                            if (spinnerModelAgv.selectedItem.toString() == agv_1100_st) {
                                val listNameAndFrequencyTO = mainApi.getT0DataAgv1100St()

                                listNameAndFrequencyTO.forEach { item ->
                                    mainApi.saveAgvTo(
                                        NameTO(
                                            null,
                                            item.nameTo,
                                            serialNumAgv.text.toString(),
                                            item.frequencyTo,
                                            "1",
                                            thisCurrentTime
                                        )
                                    )
                                }
                                Toast.makeText(
                                    context,
                                    "Добавлен agv_1100_st c S/N: ${serialNumAgv.text}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            //-----------------------------agv_1100_2p------------------------------
                            //----------------------------------------------------------------------
                            if (spinnerModelAgv.selectedItem.toString() == agv_1100_2p) {
                                val listNameAndFrequencyTO = mainApi.getT0DataAgv11002P()

                                listNameAndFrequencyTO.forEach { item ->
                                    mainApi.saveAgvTo(
                                        NameTO(
                                            null,
                                            item.nameTo,
                                            serialNumAgv.text.toString(),
                                            item.frequencyTo,
                                            "1",
                                            thisCurrentTime
                                        )
                                    )
                                }
                                Toast.makeText(
                                    context,
                                    "Добавлен agv_1100_2p c S/N: ${serialNumAgv.text}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            //-----------------------------agv_1100_2t------------------------------
                            //----------------------------------------------------------------------
                            if (spinnerModelAgv.selectedItem.toString() == agv_1100_2t) {
                                val listNameAndFrequencyTO = mainApi.getT0DataAgv11002t()

                                listNameAndFrequencyTO.forEach { item ->
                                    mainApi.saveAgvTo(
                                        NameTO(
                                            null,
                                            item.nameTo,
                                            serialNumAgv.text.toString(),
                                            item.frequencyTo,
                                            "1",
                                            thisCurrentTime
                                        )
                                    )
                                }
                                Toast.makeText(
                                    context,
                                    "Добавлен agv_1100_2t c S/N: ${serialNumAgv.text}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            //----------------------------agv_3000_st-------------------------------
                            //----------------------------------------------------------------------
                            if (spinnerModelAgv.selectedItem.toString() == agv_3000_st) {
                                val listNameAndFrequencyTO = mainApi.getT0DataAgv3000St()

                                listNameAndFrequencyTO.forEach { item ->
                                    mainApi.saveAgvTo(
                                        NameTO(
                                            null,
                                            item.nameTo,
                                            serialNumAgv.text.toString(),
                                            item.frequencyTo,
                                            "1",
                                            thisCurrentTime
                                        )
                                    )
                                }
                                Toast.makeText(
                                    context,
                                    "Добавлен agv_3000_st c S/N: ${serialNumAgv.text}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            //--------------------------------else----------------------------------
//                            else {
//
//                                for ((key, value) in TOData.toMap) {
//                                    println("Ключ: $key, Значение: $value")
//
//                                    mainApi.saveAgvTo(
//                                        NameTO(
//                                            null,
//                                            key,
//                                            serialNumAgv.text.toString(),
//                                            value,
//                                            "1",
//                                            thisCurrentTime
//                                        )
//                                    )
//                                }
//                                Toast.makeText(
//                                    context,
//                                    "Добавлен AGV c S/N: ${serialNumAgv.text}",
//                                    Toast.LENGTH_LONG
//                                ).show()
//                            }
                            //----------------------------------------------------------------------

                            nameAgv.text.clear()
                            serialNumAgv.text.clear()
                            spinnerFW.setSelection(0)
                            spinnerModelAgv.setSelection(0) // Сброс выбора в Spinner
                            spinnerEPlan.setSelection(0) // Сброс выбора в Spinner
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


//        buttonSaveImage.setOnClickListener {
//            uploadImage(
//                ImageData(uploadToServerImages.bitmapToBase64(requireContext()), "test.png")
//            )
//
//        }
        return view
    }


    /**
     * *****************************************save image****************************************
     */
    fun uploadImage(imageData: ImageData) {
        CoroutineScope(Dispatchers.IO).launch {
            val imageResponse = mainApi.uploadImage(imageData)
            Log.d("MyLog", "imageResponseURL ${imageResponse.url}")
            Log.d("MyLog", "imageResponseMessage ${imageResponse.message}")
        }
    }


    /**
     * ********************************************************************************************
     */

    companion object {
        @JvmStatic
        fun newInstance() = AgvSaveFragment()
    }

    // взятие текущего времени
    private fun getCurrentTime(): String {
        val currentTimeMillis = System.currentTimeMillis()
        return currentTimeMillis.toString()
    }

    override fun onResume() {
        super.onResume()
        loadModelAndInitAdapter() // Обновляем данные при возвращении к фрагменту
        loadFWAndInitAdapter()
        loadEPlanSchemesAndInitAdapter()
    }


    //-------------------------------------------model----------------------------------------------
    private fun loadModelAndInitAdapter() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val listModelAgv = mainApi.getAllModelAGV()
                agvModels = listModelAgv.map { it.model }.toTypedArray()

                // Настройка адаптера для Spinner после загрузки данных
                val adapter =
                    ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, agvModels)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerModelAgv.adapter = adapter
            } catch (e: Exception) {
                // Обработка ошибки
                Toast.makeText(context, "Ошибка загрузки моделей: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    //---------------------------------------------fw-----------------------------------------------
    private fun loadFWAndInitAdapter() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val listFW = mainApi.getDir().directories

                dirCompos = listFW.toTypedArray()

                // Настройка адаптера для Spinner после загрузки данных
                val adapterFW =
                    ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, dirCompos)
                adapterFW.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerFW.adapter = adapterFW
            } catch (e: Exception) {
                // Обработка ошибки
                Toast.makeText(context, "Ошибка загрузки FW: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    //-------------------------------------------ePlane---------------------------------------------
    private fun loadEPlanSchemesAndInitAdapter() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val listEPlane = mainApi.getDirAndFilesSchemes().directories

                ePlanScheme = listEPlane.toTypedArray()

                // Настройка адаптера для Spinner после загрузки данных
                val adapterEPlane =
                    ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_spinner_item,
                        ePlanScheme
                    )
                adapterEPlane.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerEPlan.adapter = adapterEPlane
            } catch (e: Exception) {
                // Обработка ошибки
                Toast.makeText(context, "Ошибка загрузки FW: ${e.message}", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    /**
     * --------------------------------------ePlan pdf-------------------------------------------
     */
//    private fun loadEPlanSchemesAndInitAdapter() {
//        CoroutineScope(Dispatchers.Main).launch {
//            try {
//                val listEPlane = mainApi.getDirAndFilesSchemes().files
//
//                // Убираем последние 4 символа у каждого файла
//                ePlanScheme = listEPlane.map { fileName ->
//                    if (fileName.length > 4) {
//                        fileName.substring(0, fileName.length - 4)
//                    } else {
//                        fileName // Если длина имени файла меньше 4, оставляем его без изменений
//                    }
//                }.toTypedArray()
//
//                // Настройка адаптера для Spinner после загрузки данных
//                val adapterEPlane = ArrayAdapter(
//                    requireContext(),
//                    android.R.layout.simple_spinner_item,
//                    ePlanScheme
//                )
//                adapterEPlane.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//                spinnerEPlan.adapter = adapterEPlane
//            } catch (e: Exception) {
//                // Обработка ошибки
//                Toast.makeText(context, "Ошибка загрузки FW: ${e.message}", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        }
//    }
    /**
     * ---------------------------------------------------------------------------------------------
     */
}