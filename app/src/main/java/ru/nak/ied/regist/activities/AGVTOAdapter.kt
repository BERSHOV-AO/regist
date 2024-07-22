package ru.nak.ied.regist.activities

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.nak.ied.regist.R
import ru.nak.ied.regist.entities.NameTO
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AGVTOAdapter(private val context: Context, private val agvToList: List<NameTO>)
    : RecyclerView.Adapter<AGVTOAdapter.AGVViewHolder>() {

    inner class AGVViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTexTO: TextView = itemView.findViewById(R.id.tvNameTO)
        val dataLastTO: TextView = itemView.findViewById(R.id.tvDataLastTO)
        val dataFutureTO: TextView = itemView.findViewById(R.id.tvDataFutureTO)
        val statusTO: TextView = itemView.findViewById(R.id.tvStatusTO)
    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AGVViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_agv_to, parent, false)
            return AGVViewHolder(view)
        }

        override fun onBindViewHolder(holder: AGVViewHolder, position: Int) {
            val currentItem = agvToList[position]

            holder.nameTexTO.text = currentItem.nameTo
            holder.dataLastTO.text = convertTime(currentItem.dataTo)
            holder.dataFutureTO.text = convertTimeWithDays(currentItem.dataTo, currentItem.frequencyOfTo)

            if(currentItem.statusTo){
                holder.statusTO.text = "OK"
                holder.statusTO.setTextColor(ContextCompat.getColor(context, android.R.color.holo_green_dark))
            } else {
                holder.statusTO.text = "NOK"
                holder.statusTO.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))
            }

        }

        override fun getItemCount(): Int {
            return agvToList.size
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
        val outputFormat = SimpleDateFormat("hh:mm:ss - dd/MM/yyyy", Locale.getDefault())
        return outputFormat.format(date)
    }

    fun convertTimeWithDays(timeString: String, daysString: String): String {
        if (timeString.isEmpty() || daysString.isEmpty()) {
            return "Некорректные данные"
        }

        val milliseconds = timeString.toLongOrNull() ?: 0
        val days = daysString.toIntOrNull() ?: 0

        if (milliseconds == 0L || days == 0) {
            return "Некорректные данные"
        }

        val date = Date(milliseconds)
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.DAY_OF_YEAR, days)

        val outputFormat = SimpleDateFormat("hh:mm:ss - dd/MM/yyyy", Locale.getDefault())
        return outputFormat.format(calendar.time)
    }

    }


