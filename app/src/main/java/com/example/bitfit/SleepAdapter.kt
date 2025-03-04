package com.example.bitfit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SleepAdapter(private val sleeps: List<Sleep>) : RecyclerView.Adapter<SleepAdapter.ViewHolder>()
{
    class ViewHolder(sleepView: View) : RecyclerView.ViewHolder(sleepView) {
        val dateTextView: TextView
        val hoursTextView: TextView
        val minTextView: TextView
        init {
            dateTextView = sleepView.findViewById(R.id.dateText)
            hoursTextView = sleepView.findViewById(R.id.hoursText)
            minTextView = sleepView.findViewById(R.id.minText)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val contactView = inflater.inflate(R.layout.sleep_item, parent, false)
        return ViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return sleeps.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sleep = sleeps.get(position)
        holder.dateTextView.text = sleep.date
        holder.hoursTextView.text = sleep.hours + " hours"
        holder.minTextView.text = sleep.minutes + " minutes"
    }
}