package com.example.reportcity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.reportcity.entities.Notas

class NotasAdapter : ListAdapter<Notas, NotasAdapter.NotasViewHolder>(notasComparator()) {
        private lateinit var onItemClickListener : onItemClick

        public fun setOnItemClick(newOnItemClickListener: NotasAdapter.onItemClick) {
            onItemClickListener= newOnItemClickListener
        }

        interface onItemClick {
            fun onViewClick(position: Int)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotasViewHolder {
            return NotasViewHolder.create(parent, onItemClickListener)
        }

        override fun onBindViewHolder(holder: NotasViewHolder, position: Int) {
            val current = getItem(position)
            holder.bind(current.title, current.description)
        }

         class NotasViewHolder(itemView: View, onItemClick: onItemClick) : RecyclerView.ViewHolder(itemView) {
            private val titleItemView: TextView = itemView.findViewById(R.id.titleItem)
            private val descritionItemView: TextView = itemView.findViewById(R.id.descritionItem)
            private val imageItemView: ImageView = itemView.findViewById(R.id.imageItem)

            fun bind(title: String?, descrition: String?) {
                titleItemView.text = title
                descritionItemView.text = descrition
                imageItemView.setImageResource(R.drawable.user)
            }

            init {
                itemView.setOnClickListener { v: View ->
                    val position = adapterPosition
                    if(position != RecyclerView.NO_POSITION) {
                        onItemClick.onViewClick(position)
                    }
                }

            }

             companion object {
                 fun create(parent: ViewGroup, onItemClickListener: onItemClick): NotasViewHolder {
                     val view: View = LayoutInflater.from(parent.context)
                             .inflate(R.layout.recycler_view, parent, false)
                     return NotasViewHolder(view,onItemClickListener)
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