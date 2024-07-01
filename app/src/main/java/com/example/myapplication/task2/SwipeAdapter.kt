package com.example.myapplication.task2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class SwipeAdapter(val data: MutableList<String>):RecyclerView.Adapter<SwipeAdapter.DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.swipe_item, parent, false)
        return DataViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }




    inner class DataViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        private val textView: TextView = itemView.findViewById(R.id.nameTV)
        val contentLayout: LinearLayout = itemView.findViewById(R.id.contentLayout)
        val swipeLayout: FrameLayout = itemView.findViewById(R.id.swipeLayout)
        val btnDelete: Button = itemView.findViewById(R.id.btnDelete)

        fun bind(item: String) {
            textView.text = item
        }

        init {



            // Handle click event of delete button
            btnDelete.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    deleteItem(position)
                }
            }
        }



    }

    fun deleteItem(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

}