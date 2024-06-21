package ru.nak.ied.regist.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import ru.nak.ied.regist.R
import ru.nak.ied.regist.databinding.ActivityNewAgvBinding
import ru.nak.ied.regist.entities.AGVItem
import ru.nak.ied.regist.fragments.AgvFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class NewAgvActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewAgvBinding
    private var agv: AGVItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewAgvBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBarSettings()
        getAGV()
    }

    private fun getAGV() {
        val sAGV = intent.getSerializableExtra(AgvFragment.NEW_NOTE_KEY)
        if (sAGV != null) {
            agv = sAGV as AGVItem
            fillAGV()
        }
    }

    // заполнение
    private fun fillAGV() = with(binding) {
        edSerialNum.setText(agv?.serialNumber)
        edDescription.setText(agv?.description)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nw_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.id_save) {
            setMainResult()
        } else if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setMainResult() {
        var editState = "new"
        val tempAGV: AGVItem?
        if (agv == null) {
            tempAGV = createNewAGV()
        } else {
            editState = "update"
            tempAGV = updateAGV()
        }
        val i = Intent().apply {
            putExtra(AgvFragment.NEW_NOTE_KEY, tempAGV)
            putExtra(AgvFragment.EDIT_STATE_KEY, editState)
        }
        setResult(RESULT_OK, i)
        finish()
    }

    private fun updateAGV(): AGVItem? = with(binding) {
        return agv?.copy(
            serialNumber = edSerialNum.text.toString(),
            description = edDescription.text.toString()
        )
    }

    // + заполнение времени
    private fun createNewAGV(): AGVItem {
        return AGVItem(
            null,
            "AGV",
            binding.edSerialNum.text.toString(),
            binding.edDescription.text.toString(),
            getCurrentTime()
        )
    }

    // взятие текущего времени
    private fun getCurrentTime(): String {
        val formatter = SimpleDateFormat("hh:mm:ss - yyyy/MM/dd", Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)
    }

    // возврат назад - стрелка
    private fun actionBarSettings() {
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }
}