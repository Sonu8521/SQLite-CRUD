package com.example.insert_data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PersonAdapter(private val personList: List<Person>,private val listener: OnItemClickListener) : RecyclerView.Adapter<PersonViewHolder>() {

    interface OnItemClickListener {

        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_person, parent, false)
        return PersonViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val currentPerson = personList[position]

        holder.nameTextView.text = "Name: ${currentPerson.name}"
        holder.emailTextView.text = "Email: ${currentPerson.email}"
        holder.contactTextView.text = "Contact: ${currentPerson.contact}"
        holder.addressTextView.text = "Address: ${currentPerson.address}"

        holder.itemView.setOnClickListener {
            listener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return personList.size

    }
}