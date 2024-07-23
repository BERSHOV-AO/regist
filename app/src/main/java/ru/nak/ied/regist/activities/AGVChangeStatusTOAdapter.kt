package ru.nak.ied.regist.activities

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.nak.ied.regist.R
import ru.nak.ied.regist.entities.NameTO
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AGVChangeStatusTOAdapter(private val agvToList: List<NameTO>) :
    RecyclerView.Adapter<AGVChangeStatusTOAdapter.AGVChangeStatusViewHolder>() {


    inner class AGVChangeStatusViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        val switchStatusToName: Switch = itemView.findViewById(R.id.swStatusTOSwitch)
       // val imageStatusTo: ImageView = itemView.findViewById(R.id.ivStatusTOImage)
//            val dataLastTO: TextView = itemView.findViewById(R.id.tvDataLastTO)
//            val dataFutureTO: TextView = itemView.findViewById(R.id.tvDataFutureTO)
//            val statusTO: TextView = itemView.findViewById(R.id.tvStatusTO)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AGVChangeStatusViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_agv_to_change_status, parent, false)
        return AGVChangeStatusViewHolder(view)
    }

    override fun onBindViewHolder(holder: AGVChangeStatusViewHolder, position: Int) {
        val currentItem = agvToList[position]

        if (currentItem.statusTo) {
            holder.switchStatusToName.isChecked = true
        } else {
            holder.switchStatusToName.isChecked = false
        }

        holder.switchStatusToName.text = currentItem.nameTo

//        if (currentItem.statusTo) {
//            holder.imageStatusTo.setImageResource(R.drawable.ic_ok)
//        } else {
//            holder.imageStatusTo.setImageResource(R.drawable.ic_nok)
//        }
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
