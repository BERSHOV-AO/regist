package ru.nak.ied.regist.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView
import ru.nak.ied.regist.api.MainApi
import ru.nak.ied.regist.entities.AGVItem
import javax.inject.Inject

@AndroidEntryPoint
class ScannerActivity : AppCompatActivity(), ZBarScannerView.ResultHandler {
    private lateinit var zbView: ZBarScannerView
    var listAgv: List<AGVItem>? = null;

    @Inject
    lateinit var mainApi: MainApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        zbView = ZBarScannerView(this)
        setContentView(zbView)
    }

    override fun onResume() {
        super.onResume()
        zbView.setResultHandler(this)
        zbView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        zbView.stopCamera()
    }

    override fun handleResult(result: Result?) {
        Log.d("MyLog", "Result: ${result?.contents}")

        CoroutineScope(Dispatchers.Main).launch {
            // Получаем список AGV
            listAgv = mainApi.getAllAGV()

            var agvFound = false
            listAgv?.forEach { agv ->
                if (agv.serialNumber == result?.contents) {
                    Log.d("MyLog", "AGV найден: ${agv.serialNumber}")
                    Toast.makeText(
                        this@ScannerActivity,
                        "AGV найден: ${agv.serialNumber}",
                        Toast.LENGTH_SHORT
                    ).show()

                    agvFound = true
                    // Если AGV найден, переходим в ItemsActivity
//                    val intent = Intent(this@ScannerActivity, ItemsActivity::class.java)
//                    intent.putExtra("key", result.contents)
                    val intent = Intent(this@ScannerActivity, ShowOneAgvToActivity::class.java)
                    intent.putExtra("SERIAL_NUMBER", result.contents)
                    startActivity(intent)
                    finish() // Закрываем текущую активность
                    return@launch // Завершаем выполнение launch
                }
            }

            // Если AGV не найден, выводим сообщение и закрываем сканер
            if (!agvFound) {
                Log.d("MyLog", "AGV НЕ найден:")
                Toast.makeText(
                    this@ScannerActivity,
                    "AGV НЕ найден",
                    Toast.LENGTH_SHORT
                ).show()
                finish() // Закрываем текущую активность (сканер)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}