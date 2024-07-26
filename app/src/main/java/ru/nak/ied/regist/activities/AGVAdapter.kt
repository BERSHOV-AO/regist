package ru.nak.ied.regist.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ru.nak.ied.regist.R
//import ru.nak.ied.regist.db.AgvAdapter
import ru.nak.ied.regist.entities.AGVItem
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 *  ----> AgvAllFragment
 */

class AGVAdapter(
    private val agvList: MutableList<AGVItem>,
    private val onDeleteClick: (String) -> Unit,
    private val onShowAgvToListClick: (String) -> Unit
) :
    RecyclerView.Adapter<AGVAdapter.AGVViewHolder>() {

    inner class AGVViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val serialNumberTextView: TextView = itemView.findViewById(R.id.serialNumberTextView)
        val versionFW: TextView = itemView.findViewById(R.id.tvVersionFW)
        val model: TextView = itemView.findViewById(R.id.tvModel)
        val ePlan: TextView = itemView.findViewById(R.id.tvEPlan)
        val timeLastTo: TextView = itemView.findViewById(R.id.tvTimeOfLastTo)
        val deleteButton: ImageButton = itemView.findViewById(R.id.btnDelete) // Кнопка удаления
        val bListAgvTo: ImageButton = itemView.findViewById(R.id.btnListToAgv)

        init {
            // deleteAgvAll
            deleteButton.setOnClickListener {
                val serialNumber = serialNumberTextView.text.toString()
                onDeleteClick(serialNumber) // Вызываем callback с серийным номером
            }

            // showListToAgv
            bListAgvTo.setOnClickListener {
                val serialNumber = serialNumberTextView.text.toString()
                onShowAgvToListClick(serialNumber)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AGVViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_agv, parent, false)
        return AGVViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AGVViewHolder, position: Int) {
        val currentItem = agvList[position]

        holder.nameTextView.text = currentItem.name
        holder.serialNumberTextView.text = currentItem.serialNumber
        holder.versionFW.text = currentItem.versionFW
        holder.model.text = currentItem.model
        holder.ePlan.text = currentItem.ePlan
        holder.timeLastTo.text = convertTime(currentItem.dataLastTo)

    }

    override fun getItemCount(): Int {
        return agvList.size
    }

    fun removeItem(serialNumber: String) {
        val indexToRemove = agvList.indexOfFirst { it.serialNumber == serialNumber }
        if (indexToRemove != -1) {
            agvList.removeAt(indexToRemove)
            notifyItemRemoved(indexToRemove)
        }
    }

    fun convertTime(timeString: String): String {
        if (timeString.isEmpty()) {
            return "Некорректное время"
        }

        val milliseconds = timeString.toLongOrNull() ?: 0
        if (milliseconds == 0L) {
            return "Некорректное время"
        }

        val date = Date(milliseconds)
        val outputFormat = SimpleDateFormat("hh:mm:ss - yyyy/MM/dd", Locale.getDefault())
        return outputFormat.format(date)
    }
}