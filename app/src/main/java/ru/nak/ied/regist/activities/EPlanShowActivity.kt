package ru.nak.ied.regist.activities

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import ru.nak.ied.regist.R
import ru.nak.ied.regist.databinding.ActivityEplanShowBinding
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ortiz.touchview.TouchImageView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.nak.ied.regist.adapter.ImageAdapter
import ru.nak.ied.regist.api.MainApi
import javax.inject.Inject

//class EPlanShowActivity : AppCompatActivity() {
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var imageAdapter: ImageAdapter
//
//    @SuppressLint("MissingInflatedId")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_eplan_show)
//
//        recyclerView = findViewById(R.id.rvSchemes)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//
//        // Замените на URL-адреса ваших изображений на сервере WAMP
//        val imageUrls = listOf(
//            "http://192.168.175.152/host/schemes/scheme_1.png",
//            "http://192.168.175.152/host/schemes/scheme_2.png"
//        )
//
//        imageAdapter = ImageAdapter(imageUrls)
//        recyclerView.adapter = imageAdapter
//    }
//}


//-----------------------------------------work ok----------------------------------------------
@AndroidEntryPoint
class EPlanShowActivity : AppCompatActivity() {

    @Inject
    lateinit var mainApi: MainApi

    private lateinit var binding: ActivityEplanShowBinding
    private lateinit var imageView: ImageView

    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private var scaleFactor = 1.0f
    private var isImageScaled = false
    private var lastTouchX = 0f
    private var lastTouchY = 0f

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEplanShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val responseEPlan = intent.getStringExtra("E_PLAN").toString()
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

//        binding.image1.setImageResource(R.drawable.scheme_1)
//        binding.image1.setImageResource(R.drawable.scheme_1)
//        binding.image2.setImageResource(R.drawable.scheme_2)
//        binding.image3.setImageResource(R.drawable.scheme_3)
//        binding.image4.setImageResource(R.drawable.scheme_4)
//        binding.image5.setImageResource(R.drawable.scheme_5)
//        binding.image6.setImageResource(R.drawable.scheme_6)
//        binding.image7.setImageResource(R.drawable.scheme_7)
//        binding.image8.setImageResource(R.drawable.scheme_8)
//        binding.image9.setImageResource(R.drawable.scheme_9)
//        binding.image10.setImageResource(R.drawable.scheme_10)
//        binding.image11.setImageResource(R.drawable.scheme_11)
//        binding.image12.setImageResource(R.drawable.scheme_12)
//        binding.image13.setImageResource(R.drawable.scheme_13)
//        binding.image14.setImageResource(R.drawable.scheme_14)
//        binding.image15.setImageResource(R.drawable.scheme_15)
//        binding.image16.setImageResource(R.drawable.scheme_16)
//        binding.image17.setImageResource(R.drawable.scheme_17)
//        binding.image18.setImageResource(R.drawable.scheme_18)
//        binding.image19.setImageResource(R.drawable.scheme_19)
//        binding.image20.setImageResource(R.drawable.scheme_20)
//        binding.image21.setImageResource(R.drawable.scheme_21)
//        binding.image22.setImageResource(R.drawable.scheme_22)
//        binding.image23.setImageResource(R.drawable.scheme_23)
//        binding.image24.setImageResource(R.drawable.scheme_24)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun loadImageFromServer(imageName: String, touchImageView: TouchImageView) {
        // Запуск корутины для выполнения сетевого запроса
        CoroutineScope(Dispatchers.IO).launch {
            val response = mainApi.getImage(imageName)
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
