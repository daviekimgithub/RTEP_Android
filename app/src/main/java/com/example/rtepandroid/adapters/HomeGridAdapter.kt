package com.example.rtepandroid.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.rtepandroid.R
import com.example.rtepandroid.models.HomeGrid

class HomeGridAdapter(
    private val gridItems: ArrayList<HomeGrid>,
    private val context: Context?
) : RecyclerView.Adapter<com.example.rtepandroid.adapters.HomeGridAdapter.HomeGridHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): com.example.rtepandroid.adapters.HomeGridAdapter.HomeGridHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_home_grid,
            parent, false
        )
        return HomeGridHolder(itemView)
    }

    override fun onBindViewHolder(holder: com.example.rtepandroid.adapters.HomeGridAdapter.HomeGridHolder, position: Int) {
        holder.title.text = gridItems.get(position).title
        holder.description.text = gridItems.get(position).desc
        holder.button.text = gridItems.get(position).button
        holder.itemCV.setOnClickListener {
            if(gridItems.get(position).activity!= null) {
                val context: Context = holder.itemCV.context
                val intent = Intent(context, gridItems.get(position).activity)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return gridItems.size
    }

    class HomeGridHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.hgTitle)
        val description: TextView = itemView.findViewById(R.id.hgDesc)
        val button: AppCompatButton = itemView.findViewById(R.id.hgButton)
        val itemCV: CardView = itemView.findViewById(R.id.itemCV)

        fun bind(homeGrid: HomeGrid){

        }

    }
}
