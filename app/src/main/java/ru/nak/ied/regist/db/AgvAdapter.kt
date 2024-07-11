package ru.nak.ied.regist.db

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.nak.ied.regist.R
import ru.nak.ied.regist.databinding.AgvListItemBinding
import ru.nak.ied.regist.entities.AGVItem
import ru.nak.ied.regist.utils.HtmlManager

class AgvAdapter(private val listener: Listener) :
    ListAdapter<AGVItem, AgvAdapter.ItemHolder>(ItemComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }


    class ItemHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = AgvListItemBinding.bind(view)
        fun setData(agv: AGVItem, listener: Listener) = with(binding) {
            tvSerialNum.text = agv.serialNumber
            tvDescription.text = HtmlManager.getFromHtml(agv.description).trim()
            tvTime.text = agv.maintenanceTime
            itemView.setOnClickListener {
                listener.onClickItem(agv)
            }
            imDelete.setOnClickListener {
                listener.deleteItem(agv.id!!) // !! говорим что не будет равен null
            }
        }

        companion object {
            fun create(prent: ViewGroup): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(prent.context)
                        .inflate(R.layout.agv_list_item, prent, false)
                )
            }
        }
    }

    class ItemComparator : DiffUtil.ItemCallback<AGVItem>() {
        override fun areItemsTheSame(oldItem: AGVItem, newItem: AGVItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AGVItem, newItem: AGVItem): Boolean {
            return oldItem == newItem
        }
    }

    interface Listener {
        fun deleteItem(id: Int)
        fun onClickItem(agvItem: AGVItem)
    }
}