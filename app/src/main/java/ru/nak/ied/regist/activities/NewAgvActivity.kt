//package ru.nak.ied.regist.activities
//
//import android.annotation.SuppressLint
//import android.content.Intent
//import android.graphics.Typeface
//import android.os.Bundle
//import android.text.Spannable
//import android.text.style.ForegroundColorSpan
//import android.text.style.StyleSpan
//import android.view.Menu
//import android.view.MenuItem
//import android.view.View
//import android.view.animation.Animation
//import android.view.animation.AnimationUtils
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.content.ContextCompat
//import ru.nak.ied.regist.R
//import ru.nak.ied.regist.databinding.ActivityNewAgvBinding
//import ru.nak.ied.regist.entities.AGVItem
//import ru.nak.ied.regist.fragments.AgvFragment
//import ru.nak.ied.regist.utils.HtmlManager
//import ru.nak.ied.regist.utils.MyTouchListener
//import java.text.SimpleDateFormat
//import java.util.Calendar
//import java.util.Locale
//
//class NewAgvActivity : AppCompatActivity() {
//
//    private lateinit var binding: ActivityNewAgvBinding
//    private var agv: AGVItem? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityNewAgvBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        actionBarSettings()
//        getAGV()
//        init()
//        onClickColorPicker()
//    }
//
//    private fun onClickColorPicker() = with(binding) {
//        imbReed.setOnClickListener() {
//            setColorForSelectedText(R.color.picker_red)
//        }
//        imbBlack.setOnClickListener {
//            setColorForSelectedText(R.color.picker_black)
//        }
//        imbBlue.setOnClickListener {
//            setColorForSelectedText(R.color.picker_blue)
//        }
//        imbYellow.setOnClickListener {
//            setColorForSelectedText(R.color.picker_yellow)
//        }
//        imbGreen.setOnClickListener {
//            setColorForSelectedText(R.color.picker_green)
//        }
//        imbOrange.setOnClickListener {
//            setColorForSelectedText(R.color.picker_orange)
//        }
//    }
//
//    @SuppressLint("ClickableViewAccessibility")
//    private fun init() {
//        binding.colorPicker.setOnTouchListener(MyTouchListener())
//    }
//
//    private fun getAGV() {
//        val sAGV = intent.getSerializableExtra(AgvFragment.NEW_NOTE_KEY)
//        if (sAGV != null) {
//            agv = sAGV as AGVItem
//            fillAGV()
//        }
//    }
//
//    // заполнение
//    private fun fillAGV() = with(binding) {
//        edSerialNum.setText(agv?.serialNumber)
//        edDescription.setText(HtmlManager.getFromHtml(agv?.description!!).trim())
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.nw_note_menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    // слушатель нажатия
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if (item.itemId == R.id.id_save) {
//            setMainResult()
//        } else if (item.itemId == android.R.id.home) {
//            finish()
//        } else if (item.itemId == R.id.id_bold) {
//            setBoldForSelectedText()
//        } else if (item.itemId == R.id.id_color) {
//            // данная функция показыват - показывает ли Shown на экране
//            if (binding.colorPicker.isShown) {
//                closeColorPicker()
//            } else {
//                openColorPicker()
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }
//
//    private fun setBoldForSelectedText() = with(binding) {
//        val startPos = edDescription.selectionStart
//        val endPos = edDescription.selectionEnd
//
//        // смотрим есть ли какой стиль у данного отрезка
//        val styles = edDescription.text.getSpans(startPos, endPos, StyleSpan::class.java)
//        var boldStyle: StyleSpan? = null
//        if (styles.isNotEmpty()) {
//            edDescription.text.removeSpan(styles[0])
//        } else {
//            boldStyle = StyleSpan(Typeface.BOLD)
//        }
//        // тип добавления Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//        edDescription.text.setSpan(boldStyle, startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        // так как все идет в  виде html, нам нужно убрать пробелы.
//        edDescription.text.trim()
//        // перемещаем курсор в начало выделенного текста
//        edDescription.setSelection(startPos)
//    }
//
//    private fun setColorForSelectedText(colorId: Int) = with(binding) {
//        val startPos = edDescription.selectionStart
//        val endPos = edDescription.selectionEnd
//
//        // смотрим есть ли какой стиль c цветом у данного отрезка
//        val styles = edDescription.text.getSpans(startPos, endPos, ForegroundColorSpan::class.java)
//        if (styles.isNotEmpty()) edDescription.text.removeSpan(styles[0])
//
//
//        // тип добавления Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//        edDescription.text.setSpan(
//            ForegroundColorSpan(ContextCompat.getColor(this@NewAgvActivity, colorId)),
//            startPos, endPos, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
//        )
//        // так как все идет в  виде html, нам нужно убрать пробелы.
//        edDescription.text.trim()
//        // перемещаем курсор в начало выделенного текста
//        edDescription.setSelection(startPos)
//    }
//
//    private fun setMainResult() {
//        var editState = "new"
//        val tempAGV: AGVItem?
//        if (agv == null) {
//            tempAGV = createNewAGV()
//        } else {
//            editState = "update"
//            tempAGV = updateAGV()
//        }
//        val i = Intent().apply {
//            putExtra(AgvFragment.NEW_NOTE_KEY, tempAGV)
//            putExtra(AgvFragment.EDIT_STATE_KEY, editState)
//        }
//        setResult(RESULT_OK, i)
//        finish()
//    }
//
//    private fun updateAGV(): AGVItem? = with(binding) {
//        return agv?.copy(
//            serialNumber = edSerialNum.text.toString(),
//            description = HtmlManager.toHtml(edDescription.text)
//        )
//    }
//
//    // + заполнение времени
//    private fun createNewAGV(): AGVItem {
//        return AGVItem(
//            null,
//            "AGV",
//            binding.edSerialNum.text.toString(),
//            HtmlManager.toHtml(binding.edDescription.text),
//            getCurrentTime()
//        )
//    }
//
//    // взятие текущего времени
//    private fun getCurrentTime(): String {
//        val formatter = SimpleDateFormat("hh:mm:ss - yyyy/MM/dd", Locale.getDefault())
//        return formatter.format(Calendar.getInstance().time)
//    }
//
//    // возврат назад - стрелка
//    private fun actionBarSettings() {
//        val ab = supportActionBar
//        ab?.setDisplayHomeAsUpEnabled(true)
//    }
//
//    private fun openColorPicker() {
//        binding.colorPicker.visibility = View.VISIBLE
//        val openAnim = AnimationUtils.loadAnimation(this, R.anim.open_color_picker)
//        binding.colorPicker.startAnimation(openAnim)
//    }
//
//    private fun closeColorPicker() {
//
//        val openAnim = AnimationUtils.loadAnimation(this, R.anim.close_color_picker)
//        // для того что бы понять когда закрывать, добавляем анимации слушатель, AnimationListener
//        openAnim.setAnimationListener(object : Animation.AnimationListener {
//            override fun onAnimationStart(animation: Animation?) {
//            }
//
//            override fun onAnimationEnd(animation: Animation?) {
//                binding.colorPicker.visibility = View.GONE
//
//            }
//
//            override fun onAnimationRepeat(animation: Animation?) {
//            }
//
//        })
//        binding.colorPicker.startAnimation(openAnim)
//    }
//}