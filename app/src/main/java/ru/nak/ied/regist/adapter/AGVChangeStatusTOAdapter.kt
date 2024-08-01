package ru.nak.ied.regist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.recyclerview.widget.RecyclerView
import ru.nak.ied.regist.R
import ru.nak.ied.regist.entities.NameTO
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AGVChangeStatusTOAdapter(
    private var agvToList: MutableList<NameTO>,
    private val onSwitchChanged: (Int, Boolean, String) -> Unit
) : RecyclerView.Adapter<AGVChangeStatusTOAdapter.AGVChangeStatusViewHolder>() {

    inner class AGVChangeStatusViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        val switchStatusToName: Switch = itemView.findViewById(R.id.swStatusTOSwitch)

        init {
            // Устанавливаем слушатель для переключателя
            switchStatusToName.setOnCheckedChangeListener { _, isChecked ->
                // Вызываем обратный вызов при изменении состояния
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val currentItem = agvToList[position]
                    onSwitchChanged(position, isChecked, currentItem.nameTo)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AGVChangeStatusViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_agv_to_change_status, parent, false)
        return AGVChangeStatusViewHolder(view)
    }

    override fun onBindViewHolder(holder: AGVChangeStatusViewHolder, position: Int) {
        val currentItem = agvToList[position]

        holder.switchStatusToName.isChecked = currentItem.statusTo
        holder.switchStatusToName.text = currentItem.nameTo
    }

    //----------------------work------------------------------
//    fun removeItem(serialNumber: String, positionToAgv: Int?) {
//        // Проверяем, что позиция не равна null и не равна -1
//        if (positionToAgv != null && positionToAgv >= 0) {
//            agvToList.removeAt(positionToAgv)
//            notifyItemRemoved(positionToAgv) // Уведомляем адаптер о том, что элемент был удалён
//        }
//    }
    //---------------------------------------------------------

//    fun removeItem(serialNumber: String) {
//        val indexToRemove = agvToList.indexOfFirst { it.serialNumberAGV == serialNumber }
//        if (indexToRemove != -1) {
//            agvToList.removeAt(indexToRemove)
//            notifyItemRemoved(indexToRemove)
//        }
//    }

    fun removeItem(nameTO: String) {
        val indexToRemove = agvToList.indexOfFirst { it.nameTo == nameTO }
        if (indexToRemove != -1) {
            agvToList.removeAt(indexToRemove)
            notifyItemRemoved(indexToRemove)
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
