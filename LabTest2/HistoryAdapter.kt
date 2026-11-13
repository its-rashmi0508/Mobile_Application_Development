package com.example.ecotrack

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(
    private var entries: List<WaterEntry>,
    private val onDeleteClick: (WaterEntry) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDrinkType: TextView = view.findViewById(R.id.tvDrinkType)
        val tvLocation: TextView = view.findViewById(R.id.tvLocation)
        val tvTimestamp: TextView = view.findViewById(R.id.tvTimestamp)
        val tvAmount: TextView = view.findViewById(R.id.tvAmount)
        val ivDrinkIcon: ImageView = view.findViewById(R.id.ivDrinkIcon)
        val btnDelete: ImageButton = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_water_entry, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entry = entries[position]
        holder.tvDrinkType.text = entry.drinkType
        holder.tvAmount.text = "${entry.amount} ml"
        holder.tvTimestamp.text = entry.timestamp

        val locText = if (entry.locationName.isNotEmpty() && entry.locationName != "Unknown Location") {
            entry.locationName
        } else if (entry.latitude != 0.0 || entry.longitude != 0.0) {
            "Lat: %.4f, Lng: %.4f".format(entry.latitude, entry.longitude)
        } else {
            "Location Not Available"
        }
        holder.tvLocation.text = locText

        val iconRes = when (entry.drinkType.lowercase()) {
            "tea" -> android.R.drawable.ic_menu_crop
            "juice" -> android.R.drawable.ic_menu_today
            "coffee" -> android.R.drawable.ic_menu_day
            else -> android.R.drawable.ic_menu_compass
        }
        holder.ivDrinkIcon.setImageResource(iconRes)

        holder.btnDelete.setOnClickListener {
            onDeleteClick(entry)
        }
    }

    override fun getItemCount(): Int = entries.size

    fun updateData(newEntries: List<WaterEntry>) {
        entries = newEntries
        notifyDataSetChanged()
    }
}
