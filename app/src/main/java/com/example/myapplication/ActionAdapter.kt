package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ActionAdapter(private val items: List<Action>) :
    RecyclerView.Adapter<ActionAdapter.ActionViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionViewHolder {
        return ActionViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.action_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ActionViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount() = items.size

    class ActionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(action: Action) {
            itemView.findViewById<TextView>(R.id.timeTv).text = action.getDate()
            itemView.findViewById<TextView>(R.id.stateTv).text = action.state
        }
    }


}