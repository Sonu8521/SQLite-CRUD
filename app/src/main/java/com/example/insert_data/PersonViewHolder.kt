package com.example.insert_data

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PersonViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
    val emailTextView: TextView = itemView.findViewById(R.id.emailTextView)
    val contactTextView: TextView = itemView.findViewById(R.id.contactTextView)
    val addressTextView: TextView = itemView.findViewById(R.id.addressTextView)
}