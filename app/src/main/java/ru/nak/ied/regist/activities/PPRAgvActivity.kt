package ru.nak.ied.regist.activities

import android.app.AlertDialog
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.nak.ied.regist.R
import ru.nak.ied.regist.api.MainApi
import ru.nak.ied.regist.databinding.ActivityDiagnosticsAgvBinding
import ru.nak.ied.regist.entities.AGVItem
import ru.nak.ied.regist.entities.AgvDiagnostic
import javax.inject.Inject

@AndroidEntryPoint
class PPRAgvActivity : AppCompatActivity() {

    @Inject
    lateinit var mainApi: MainApi

    private lateinit var binding: ActivityDiagnosticsAgvBinding

    var diagnosticShassi: Boolean = false;
    var diagnosticsBattery: Boolean = false;
    var diagnosticSensoryPanel: Boolean = false;
    var diagnosticsPin: Boolean = false;
    var diagnosticLaserScanner: Boolean = false;
    var diagnosticRfidReader: Boolean = false;
    var diagnosticSoundSignal: Boolean = false;
    var diagnosticLightIndication: Boolean = false;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDiagnosticsAgvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val responseSerialNum = intent.getStringExtra("agvNumDiagnostic");

        binding.tvSerNum.text = responseSerialNum


        //***********************************switch******************************************
        binding.swDiagnosticShassi.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.ivDiagnosticShassi.setImageResource(R.drawable.ic_ok)
            } else {
                binding.ivDiagnosticShassi.setImageResource(R.drawable.ic_nok)
            }
        }

        binding.swDiagnosticsBattery.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.ivDiagnosticsBattery.setImageResource(R.drawable.ic_ok)
            } else {
                binding.ivDiagnosticsBattery.setImageResource(R.drawable.ic_nok)
            }
        }

        binding.swDiagnosticSensoryPanel.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.ivDiagnosticSensoryPanel.setImageResource(R.drawable.ic_ok)
            } else {
                binding.ivDiagnosticSensoryPanel.setImageResource(R.drawable.ic_nok)
            }
        }

        binding.swpDiagnosticsPin.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.ivDiagnosticsPin.setImageResource(R.drawable.ic_ok)
            } else {
                binding.ivDiagnosticsPin.setImageResource(R.drawable.ic_nok)
            }
        }

        binding.swDiagnosticLaserScanner.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.ivDiagnosticLaserScanner.setImageResource(R.drawable.ic_ok)
            } else {
                binding.ivDiagnosticLaserScanner.setImageResource(R.drawable.ic_nok)
            }
        }

        binding.swDiagnosticRfidReader.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.ivDiagnosticRfidReader.setImageResource(R.drawable.ic_ok)
            } else {
                binding.ivDiagnosticRfidReader.setImageResource(R.drawable.ic_nok)
            }
        }

        binding.swDiagnosticSoundSignal.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.ivDiagnosticSoundSignal.setImageResource(R.drawable.ic_ok)
            } else {
                binding.ivDiagnosticSoundSignal.setImageResource(R.drawable.ic_nok)
            }
        }

        binding.swDiagnosticLightIndication.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                binding.ivDiagnosticLightIndication.setImageResource(R.drawable.ic_ok)
            } else {
                binding.ivDiagnosticLightIndication.setImageResource(R.drawable.ic_nok)
            }
        }

        //***********************************************************************************

        binding.bSaveDiagnostic.setOnClickListener {

            val dialogView = layoutInflater.inflate(R.layout.dialog_password, null)
            val etDialogPassword = dialogView.findViewById<EditText>(R.id.etDialogPassword)
            diagnosticShassi = binding.swDiagnosticShassi.isChecked
            diagnosticsBattery = binding.swDiagnosticsBattery.isChecked
            diagnosticSensoryPanel = binding.swDiagnosticSensoryPanel.isChecked
            diagnosticsPin = binding.swpDiagnosticsPin.isChecked
            diagnosticLaserScanner = binding.swDiagnosticLaserScanner.isChecked
            diagnosticRfidReader = binding.swDiagnosticRfidReader.isChecked
            diagnosticSoundSignal = binding.swDiagnosticSoundSignal.isChecked
            diagnosticLightIndication = binding.swDiagnosticLightIndication.isChecked


            AlertDialog.Builder(this)
                .setTitle("Введите пароль для сохранения данных диагностики AGV s/n: $responseSerialNum")
                .setView(dialogView)
                .setPositiveButton("OK") { dialog, _ ->
                    val enteredPassword = etDialogPassword.text.toString()
                    if (enteredPassword == "121286") {
                        Toast.makeText(this, "Пароль верный", Toast.LENGTH_SHORT).show()

                        CoroutineScope(Dispatchers.Main).launch {

                            val diagnosticTime: String = getCurrentTime()
                            mainApi.saveDiagnosticAgv(
                                AgvDiagnostic(
                                    null,
                                    responseSerialNum.toString(),
                                    diagnosticShassi,
                                    diagnosticsBattery,
                                    diagnosticSensoryPanel,
                                    diagnosticsPin,
                                    diagnosticLaserScanner,
                                    diagnosticRfidReader,
                                    diagnosticSoundSignal,
                                    diagnosticLightIndication,
                                    diagnosticTime
                                )
                            )

                            mainApi.saveOrUpdateAgv(
                                AGVItem(
                                    null,
                                    "AGV",
                                    responseSerialNum.toString(),
                                    "update Diagnostic",
                                    diagnosticTime
                                )
                            )
                        }
                    } else {
                        // Пароль неверный, показать сообщение об ошибке
                        Toast.makeText(this, "Неверный пароль", Toast.LENGTH_SHORT).show()
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("Отмена") { dialog, _ ->
                    dialog.dismiss()
                }.show()
        }
    }

    private fun getCurrentTime(): String {
        val currentTimeMillis = System.currentTimeMillis()
        return currentTimeMillis.toString()
    }
}