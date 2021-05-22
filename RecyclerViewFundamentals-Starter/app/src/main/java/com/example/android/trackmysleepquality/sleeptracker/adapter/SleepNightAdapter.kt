package com.example.android.trackmysleepquality.sleeptracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android.trackmysleepquality.R
import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString
import com.example.android.trackmysleepquality.database.SleepNight
import com.example.android.trackmysleepquality.databinding.ListItemSleepNightBinding
import kotlinx.android.synthetic.main.list_item_sleep_night.view.*

class SleepNightAdapter : RecyclerView.Adapter<SleepNightAdapter.ViewHolder>() {
    var data = listOf<SleepNight>()
        set(value) { //set & update data
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = DataBindingUtil.inflate<ListItemSleepNightBinding>(
            layoutInflater,
            R.layout.list_item_sleep_night,
            parent,
            false
        )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemView = data[position]
        holder.onBind(itemView, position)
    }

    override fun getItemCount(): Int {
        return data.size
    }


    // ViewHolder
    class ViewHolder(itemView: ListItemSleepNightBinding) : RecyclerView.ViewHolder(itemView.root) {
        private lateinit var sleepNight: SleepNight
        private var mPosition = 0
        fun onBind(sleepNight: SleepNight, position: Int) {
            this.sleepNight = sleepNight
            this.mPosition = position
            itemView.sleep_length.text = convertDurationToFormatted(
                sleepNight.startTimeMilli,
                sleepNight.endTimeMilli,
                itemView.resources
            )
            itemView.quality_string.text =
                convertNumericQualityToString(sleepNight.sleepQuality, itemView.resources)
            itemView.quality_image.setImageResource(
                when (sleepNight.sleepQuality) {
                    0 -> R.drawable.ic_sleep_0
                    1 -> R.drawable.ic_sleep_1
                    2 -> R.drawable.ic_sleep_2
                    3 -> R.drawable.ic_sleep_3
                    4 -> R.drawable.ic_sleep_4
                    5 -> R.drawable.ic_sleep_5
                    else -> R.drawable.ic_sleep_active
                }
            )

        }

    }
}