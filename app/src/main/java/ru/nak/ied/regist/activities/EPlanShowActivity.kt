package ru.nak.ied.regist.activities

import android.os.Bundle
import android.view.ScaleGestureDetector
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import ru.nak.ied.regist.R
import ru.nak.ied.regist.databinding.ActivityAuthBinding
import ru.nak.ied.regist.databinding.ActivityEplanShowBinding


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
    private var scaleFactor = 1f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEplanShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val responseEPlan = intent.getStringExtra("E_PLAN").toString()
        supportActionBar?.title = "ePlan: $responseEPlan"

        binding.tvEPlan.text = responseEPlan

        imageView = findViewById(R.id.image1)
        Glide.with(this).load(R.drawable.scheme_1).into(imageView)


//        val scaleGestureDetector = ScaleGestureDetector(this, ScaleListener())
//        imageView.setOnTouchListener { _, event ->
//            scaleGestureDetector.onTouchEvent(event)
//            true
//        }
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
    }

//    private inner class ScaleListener : ScaleGestureDetector.SimpleOnScaleGestureListener() {
//        override fun onScale(detector: ScaleGestureDetector): Boolean {
//            scaleFactor *= detector.scaleFactor
//            scaleFactor = maxOf(0.1f, minOf(scaleFactor, 5.0f)) // Ограничение масштаба
//            imageView.scaleX = scaleFactor
//            imageView.scaleY = scaleFactor
//            return true
//        }
//    }
}

//binding.image1.setImageResource(R.drawable.scheme_1)
//        Glide.with(this)
//            .load(R.drawable.scheme_1) // Замените на ваш ресурс
//            .into(binding.image1)


//binding.image2.setImageResource(R.drawable.scheme_2)
//binding.image3.setImageResource(R.drawable.scheme_3)
//binding.image4.setImageResource(R.drawable.scheme_4)
//binding.image5.setImageResource(R.drawable.scheme_5)
//binding.image6.setImageResource(R.drawable.scheme_6)
//binding.image7.setImageResource(R.drawable.scheme_7)
//binding.image8.setImageResource(R.drawable.scheme_8)
//binding.image9.setImageResource(R.drawable.scheme_9)