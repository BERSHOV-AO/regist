package ru.nak.ied.regist.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import ru.nak.ied.regist.R
import ru.nak.ied.regist.databinding.ActivityEplanShowBinding
import android.view.MotionEvent


//class EPlanShowActivity : AppCompatActivity() {
//
//    private lateinit var scaleGestureDetector: ScaleGestureDetector
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_eplan_show) // Укажите ваш layout файл
//
//        val image1 = findViewById<ImageView>(R.id.image1)
//        val image2 = findViewById<ImageView>(R.id.image2)
//
//        scaleGestureDetector = ScaleGestureDetector(this, ScaleListener(image1))
//
//        image1.setOnTouchListener { v, event ->
//            scaleGestureDetector.onTouchEvent(event)
//            true
//        }
//
//        image2.setOnTouchListener { v, event ->
//            scaleGestureDetector.onTouchEvent(event)
//            true
//        }
//    }
//
//    private inner class ScaleListener(private val imageView: ImageView) : ScaleGestureDetector.SimpleOnScaleGestureListener() {
//        private var scaleFactor = 1.0f
//
//        override fun onScale(detector: ScaleGestureDetector): Boolean {
//            scaleFactor *= detector.scaleFactor
//            scaleFactor = maxOf(0.1f, minOf(scaleFactor, 5.0f)) // Ограничение масштаба
//
//            // Применяем масштаб только к выбранному ImageView
//            imageView.scaleX = scaleFactor
//            imageView.scaleY = scaleFactor
//
//            return true
//        }
//    }
//}
//
//
//
////    private inner class ScaleListener(private val imageViews: List<ImageView>) : ScaleGestureDetector.SimpleOnScaleGestureListener() {
////        override fun onScale(detector: ScaleGestureDetector): Boolean {
////            scaleFactor *= detector.scaleFactor
////            scaleFactor = maxOf(0.1f, minOf(scaleFactor, 5.0f)) // Ограничение масштаба
////
////            // Применяем масштаб ко всем ImageView
////            imageViews.forEach { imageView ->
////                imageView.scaleX = scaleFactor
////                imageView.scaleY = scaleFactor
////            }
////            return true
////        }
////    }
////}


class EPlanShowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEplanShowBinding
    private lateinit var imageView: ImageView
  //  private var scaleFactor = 1f

  //  private var isImageScaled = false


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


       // val img_1 = findViewById<View>(R.id.img_1) as ImageView
//        binding.image1.setOnClickListener { v: View ->
//            if (!isImageScaled) v.animate().scaleX(1.4f).scaleY(1.4f).setDuration(500)
//            if (isImageScaled) v.animate().scaleX(1f).scaleY(1f).setDuration(500)
//            isImageScaled = !isImageScaled
//        }

//        val imageView = binding.image1
//
//        scaleGestureDetector = ScaleGestureDetector(this, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
//            override fun onScale(detector: ScaleGestureDetector): Boolean {
//                scaleFactor *= detector.scaleFactor
//                scaleFactor = maxOf(0.1f, minOf(scaleFactor, 5.0f)) // Ограничиваем масштаб
//                imageView.scaleX = scaleFactor
//                imageView.scaleY = scaleFactor
//                return true // Указываем, что событие обработано
//            }
//        })
//
//        // Установка OnTouchListener для обработки жестов
//        imageView.setOnTouchListener { v, event ->
//            scaleGestureDetector.onTouchEvent(event)
//
//            when (event.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    lastTouchX = event.rawX
//                    lastTouchY = event.rawY
//                    true // Обработка события начата
//                }
//                MotionEvent.ACTION_MOVE -> {
//                    if (isImageScaled) {
//                        val dx = event.rawX - lastTouchX
//                        val dy = event.rawY - lastTouchY
//                        v.x += dx
//                        v.y += dy
//                        lastTouchX = event.rawX
//                        lastTouchY = event.rawY
//                        true // Обработка события завершена
//                    } else {
//                        false // Если изображение не увеличено, не обрабатываем касания
//                    }
//                }
//                MotionEvent.ACTION_UP -> {
//                    true // Обработка события завершена
//                }
//                else -> false // Другие события не обрабатываем
//            }
//        }
//
//        // Устанавливаем слушатель клика для переключения состояния увеличения
//        imageView.setOnClickListener {
//            isImageScaled = !isImageScaled
//        }





        binding.image1.setImageResource(R.drawable.scheme_1)
        binding.image2.setImageResource(R.drawable.scheme_2)
        binding.image3.setImageResource(R.drawable.scheme_3)
        binding.image4.setImageResource(R.drawable.scheme_4)
        binding.image5.setImageResource(R.drawable.scheme_5)
        binding.image6.setImageResource(R.drawable.scheme_6)
        binding.image7.setImageResource(R.drawable.scheme_7)
        binding.image8.setImageResource(R.drawable.scheme_8)
        binding.image9.setImageResource(R.drawable.scheme_9)
        binding.image10.setImageResource(R.drawable.scheme_10)
        binding.image11.setImageResource(R.drawable.scheme_11)
        binding.image12.setImageResource(R.drawable.scheme_12)
        binding.image13.setImageResource(R.drawable.scheme_13)
        binding.image14.setImageResource(R.drawable.scheme_14)
        binding.image15.setImageResource(R.drawable.scheme_15)
        binding.image16.setImageResource(R.drawable.scheme_16)
        binding.image17.setImageResource(R.drawable.scheme_17)
        binding.image18.setImageResource(R.drawable.scheme_18)
        binding.image19.setImageResource(R.drawable.scheme_19)
        binding.image20.setImageResource(R.drawable.scheme_20)
        binding.image21.setImageResource(R.drawable.scheme_21)
        binding.image22.setImageResource(R.drawable.scheme_22)
        binding.image23.setImageResource(R.drawable.scheme_23)
        binding.image24.setImageResource(R.drawable.scheme_24)
    }
}
