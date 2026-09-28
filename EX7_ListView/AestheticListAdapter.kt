package com.example.list_view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView

class AestheticListAdapter(
    private val context: Context,
    private var items: List<Destination>,
) : BaseAdapter() {

    private var selectedPosition: Int = 0
    private var isDualPane: Boolean = false

    fun updateData(newItems: List<Destination>) {
        this.items = newItems
        this.selectedPosition = 0
        notifyDataSetChanged()
    }

    fun setSelectedPosition(position: Int) {
        if (position in items.indices) {
            this.selectedPosition = position
            notifyDataSetChanged()
        }
    }

    fun setDualPaneMode(dualPane: Boolean) {
        this.isDualPane = dualPane
        notifyDataSetChanged()
    }

    fun getSelectedItem(): Destination? {
        return items.getOrNull(selectedPosition)
    }

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Destination = items[position]

    override fun getItemId(position: Int): Long = items[position].id.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_destination, parent, false)
            holder = ViewHolder(
                cardView = view.findViewById(R.id.itemCardView),
                imageView = view.findViewById(R.id.itemImageView),
                categoryText = view.findViewById(R.id.itemCategoryText),
                ratingText = view.findViewById(R.id.itemRatingText),
                titleText = view.findViewById(R.id.itemTitleText),
                locationText = view.findViewById(R.id.itemLocationText),
            )
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val destination = getItem(position)

        holder.imageView.setImageResource(destination.imageResId)
        holder.categoryText.text = destination.category
        holder.ratingText.text = destination.rating.toString()
        holder.titleText.text = destination.title
        holder.locationText.text = destination.location

        // Selected state styling for dual-pane mode
        if (isDualPane && (position == selectedPosition)) {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.selected_item_bg))
            holder.cardView.strokeColor = ContextCompat.getColor(context, R.color.primary_emerald)
            holder.cardView.strokeWidth = 4
        } else {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.surface_card))
            holder.cardView.strokeColor = ContextCompat.getColor(context, R.color.card_stroke)
            holder.cardView.strokeWidth = 2
        }

        return view
    }

    private class ViewHolder(
        val cardView: MaterialCardView,
        val imageView: ImageView,
        val categoryText: TextView,
        val ratingText: TextView,
        val titleText: TextView,
        val locationText: TextView,
    )
}