package com.example.reportcity.ui.reports

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.reportcity.R
import com.example.reportcity.api.entities.reports

class reportsAdapter : ListAdapter<reports, reportsAdapter.reportsViewHolder>(reportsComparator()) {
        private lateinit var onItemClickListener : onItemClick

        public fun setOnItemClick(newOnItemClickListener: reportsAdapter.onItemClick) {
            onItemClickListener= newOnItemClickListener
        }

        interface onItemClick {
            fun onViewClick(position: Int)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): reportsViewHolder {
            return reportsViewHolder.create(parent, onItemClickListener)
        }

        override fun onBindViewHolder(holder: reportsViewHolder, position: Int) {
            val current = getItem(position)
            holder.bind(current.name, current.description)
        }

         class reportsViewHolder(itemView: View, onItemClick: onItemClick) : RecyclerView.ViewHolder(itemView) {
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
                 fun create(parent: ViewGroup, onItemClickListener: onItemClick): reportsViewHolder {
                     val view: View = LayoutInflater.from(parent.context)
                             .inflate(R.layout.recycler_view_reports, parent, false)
                     return reportsViewHolder(view,onItemClickListener)
                 }
             }
        }

        class reportsComparator : DiffUtil.ItemCallback<reports>() {
            override fun areItemsTheSame(oldItem: reports, newItem: reports): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: reports, newItem: reports): Boolean {
                return oldItem.name == newItem.name
            }
        }

    }