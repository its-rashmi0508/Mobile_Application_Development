package com.example.contactprovider

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class ContactAdapter(
    private var allContacts: List<Contact> = emptyList(),
    private val onContactClick: (Contact) -> Unit,
    private val onShareClick: (Contact) -> Unit
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    private var filteredContacts: List<Contact> = allContacts

    private val avatarColors = intArrayOf(
        0xFF6750A4.toInt(), // Purple
        0xFF006A60.toInt(), // Teal
        0xFF1A6585.toInt(), // Ocean Blue
        0xFF825500.toInt(), // Amber
        0xFF9A2540.toInt(), // Crimson
        0xFF3B642B.toInt(), // Emerald
        0xFF71557A.toInt(), // Deep Violet
        0xFF00639A.toInt(), // Cobalt
        0xFF8C4A00.toInt()  // Deep Orange
    )

    fun updateContacts(newContacts: List<Contact>) {
        allContacts = newContacts
        filteredContacts = newContacts
        notifyDataSetChanged()
    }

    fun filter(query: String) {
        filteredContacts = if (query.isBlank()) {
            allContacts
        } else {
            val q = query.trim().lowercase()
            allContacts.filter {
                it.name.lowercase().contains(q) || it.phoneNumber.lowercase().contains(q)
            }
        }
        notifyDataSetChanged()
    }

    fun getFilteredCount(): Int = filteredContacts.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.bind(filteredContacts[position])
    }

    override fun getItemCount(): Int = filteredContacts.size

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvAvatar: TextView = itemView.findViewById(R.id.tvAvatar)
        private val tvName: TextView = itemView.findViewById(R.id.tvName)
        private val tvPhoneNumber: TextView = itemView.findViewById(R.id.tvPhoneNumber)
        private val btnShare: MaterialButton = itemView.findViewById(R.id.btnShare)

        fun bind(contact: Contact) {
            tvName.text = contact.name
            val mobileText = itemView.context.getString(R.string.mobile_label)
            tvPhoneNumber.text = "$mobileText · ${contact.phoneNumber}"

            val initial = contact.name.firstOrNull()?.uppercaseChar() ?: '?'
            tvAvatar.text = initial.toString()

            val colorIndex = kotlin.math.abs(contact.name.hashCode()) % avatarColors.size
            tvAvatar.background?.let { drawable ->
                val wrappedDrawable = DrawableCompat.wrap(drawable.mutate())
                DrawableCompat.setTint(wrappedDrawable, avatarColors[colorIndex])
                tvAvatar.background = wrappedDrawable
            }

            itemView.setOnClickListener { onContactClick(contact) }
            btnShare.setOnClickListener { onShareClick(contact) }
        }
    }
}
