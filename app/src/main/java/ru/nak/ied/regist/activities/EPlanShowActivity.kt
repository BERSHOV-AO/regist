package ru.nak.ied.regist.activities

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ru.nak.ied.regist.databinding.ActivityEplanShowBinding
import com.ortiz.touchview.TouchImageView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Response
import ru.nak.ied.regist.api.MainApi
import javax.inject.Inject

@AndroidEntryPoint
class EPlanShowActivity : AppCompatActivity() {

    @Inject
    lateinit var mainApi: MainApi

    private lateinit var binding: ActivityEplanShowBinding

    private lateinit var responseEPlan: String

    private lateinit var response: Response<ResponseBody>

    val AGV_0002_00_01_00_001: String = "AGV-0002.00.01.00.001"
    val AGV_0002_00_01_00_001a: String = "AGV-0002.00.01.00.001a"


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEplanShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        responseEPlan = intent.getStringExtra("E_PLAN").toString()
        supportActionBar?.title = "ePlan: $responseEPlan"

        binding.tvEPlan.text = responseEPlan

        loadImageFromServer("scheme_1.png", binding.image1)
        loadImageFromServer("scheme_2.png", binding.image2)
        loadImageFromServer("scheme_3.png", binding.image3)
        loadImageFromServer("scheme_4.png", binding.image4)
        loadImageFromServer("scheme_5.png", binding.image5)
        loadImageFromServer("scheme_6.png", binding.image6)
        loadImageFromServer("scheme_7.png", binding.image7)
        loadImageFromServer("scheme_8.png", binding.image8)
        loadImageFromServer("scheme_9.png", binding.image9)
        loadImageFromServer("scheme_10.png", binding.image10)
        loadImageFromServer("scheme_11.png", binding.image11)
        loadImageFromServer("scheme_12.png", binding.image12)
        loadImageFromServer("scheme_13.png", binding.image13)
        loadImageFromServer("scheme_14.png", binding.image14)
        loadImageFromServer("scheme_15.png", binding.image15)
        loadImageFromServer("scheme_16.png", binding.image16)
        loadImageFromServer("scheme_17.png", binding.image17)
        loadImageFromServer("scheme_18.png", binding.image18)
        loadImageFromServer("scheme_19.png", binding.image19)
        loadImageFromServer("scheme_20.png", binding.image20)
        loadImageFromServer("scheme_21.png", binding.image21)
        loadImageFromServer("scheme_22.png", binding.image22)
        loadImageFromServer("scheme_23.png", binding.image23)
        loadImageFromServer("scheme_24.png", binding.image24)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun loadImageFromServer(imageName: String, touchImageView: TouchImageView) {
        // Запуск корутины для выполнения сетевого запроса
        CoroutineScope(Dispatchers.IO).launch {

            if (responseEPlan == AGV_0002_00_01_00_001) {
                response = mainApi.getScheme_AGV_0002_00_01_00_001(imageName)
            }
            if (responseEPlan == AGV_0002_00_01_00_001a) {
                response = mainApi.getScheme_AGV_0002_00_01_00_001a(imageName)
            }
            if (response.isSuccessful) {
                val inputStream = response.body()?.byteStream()
                val bitmap = BitmapFactory.decodeStream(inputStream)

                // Обновление UI на главном потоке
                withContext(Dispatchers.Main) {
                    touchImageView.setImageBitmap(bitmap) // Установка загруженного изображения в ImageView
                }
                Log.e("MyLog", "OK!!! Image")
            } else {
                // Обработка ошибки
                Log.e("MyLog", "Error: ${response.message()}")
            }
        }
    }
}
