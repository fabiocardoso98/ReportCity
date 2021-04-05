    package com.example.reportcity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.reportcity.entities.Notas

class NotasAdapter : ListAdapter<Notas, NotasAdapter.NotasViewHolder>(notasComparator()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotasViewHolder {
            return NotasViewHolder.create(parent)
        }

        override fun onBindViewHolder(holder: NotasViewHolder, position: Int) {
            val current = getItem(position)
            holder.bind(current.title)
        }

        class NotasViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val wordItemView: TextView = itemView.findViewById(R.id.textView)

            fun bind(text: String?) {
                wordItemView.text = text
            }

            companion object {
                fun create(parent: ViewGroup): NotasViewHolder {
                    val view: View = LayoutInflater.from(parent.context)
                        .inflate(R.layout.recycler_view, parent, false)
                    return NotasViewHolder(view)
                }
            }
        }

        class notasComparator : DiffUtil.ItemCallback<Notas>() {
            override fun areItemsTheSame(oldItem: Notas, newItem: Notas): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Notas, newItem: Notas): Boolean {
                return oldItem.title == newItem.title
            }
        }

    }