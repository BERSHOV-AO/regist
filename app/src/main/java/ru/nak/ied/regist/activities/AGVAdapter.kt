package ru.nak.ied.regist.activities

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ru.nak.ied.regist.R
import ru.nak.ied.regist.db.AgvAdapter
import ru.nak.ied.regist.entities.AGVItem
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AGVAdapter(private val agvList: List<AGVItem>) :
    RecyclerView.Adapter<AGVAdapter.AGVViewHolder>()  {

    inner class AGVViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        val serialNumberTextView: TextView = itemView.findViewById(R.id.serialNumberTextView)
        val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
        val maintenanceTimeTextView: TextView = itemView.findViewById(R.id.maintenanceTimeTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AGVViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_agv, parent, false)
        return AGVViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AGVViewHolder, position: Int) {
        val currentItem = agvList[position]

        holder.nameTextView.text = currentItem.name
        holder.serialNumberTextView.text = currentItem.serialNumber
//        holder.descriptionTextView.text = currentItem.description
//        holder.maintenanceTimeTextView.text = convertTime(currentItem.maintenanceTime)
    }

    override fun getItemCount(): Int {
        return agvList.size
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