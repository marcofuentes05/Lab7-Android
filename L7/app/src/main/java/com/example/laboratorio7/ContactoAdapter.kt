package com.example.laboratorio7

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.contacto_item.view.*


class ContactoAdapter : ListAdapter<Contacto, ContactoAdapter.ContactoHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Contacto>() {
            override fun areItemsTheSame(oldItem: Contacto, newItem: Contacto): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Contacto, newItem: Contacto): Boolean {
                return oldItem.nombre == newItem.nombre && oldItem.telefono == newItem.telefono
                        && oldItem.email == newItem.email && oldItem.prioridad == newItem.prioridad
            }
        }
    }

    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactoHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.contacto_item, parent, false)
        return ContactoHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactoHolder, position: Int) {
        val currentContact: Contacto = getItem(position)

        holder.textViewNombre.text = currentContact.nombre
        holder.textViewTelefono.text = currentContact.telefono.toString()
    }

    fun getContactoAt(posicion: Int): Contacto{
        return getItem(posicion)
    }

    inner class ContactoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
        }


        var textViewNombre : TextView = itemView.nombre
        var textViewTelefono : TextView = itemView.telefono

    }


    interface OnItemClickListener {
        fun onItemClick(c: Contacto)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
}