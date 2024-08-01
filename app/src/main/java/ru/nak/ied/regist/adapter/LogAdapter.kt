package ru.nak.ied.regist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.nak.ied.regist.R
import ru.nak.ied.regist.entities.LogAgv
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class LogAdapter(
    private val logList: List<LogAgv>
) :
    RecyclerView.Adapter<LogAdapter.LogViewHolder>() {

    inner class LogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val timeLogItem: TextView = itemView.findViewById(R.id.tvTimeLogItem)
        val serialNumAgvLogItem: TextView = itemView.findViewById(R.id.tvSerialNumAgvLogItem)
        val nameToLogItem: TextView = itemView.findViewById(R.id.tvNameToLogItem)
        val tabelNumberUserLogItem: TextView = itemView.findViewById(R.id.tvTabelNumberUserLogItem)
        val llAgvUserLog: LinearLayout = itemView.findViewById(R.id.llAgvUserLog)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_log, parent, false)
        return LogViewHolder(view)
    }

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        val currentItem = logList[position]
        holder.timeLogItem.text = convertTime(currentItem.timeToAgv)
        holder.serialNumAgvLogItem.text = currentItem.serialNumberAgv
        holder.nameToLogItem.text = currentItem.nameTO
        holder.tabelNumberUserLogItem.text = currentItem.tabelNum
    }

    override fun getItemCount(): Int {
        return logList.size
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