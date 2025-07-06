package com.example.noteapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

class NoteAdapter(context: Context, private val notes: List<Note>) :
    ArrayAdapter<Note>(context, R.layout.note_item, notes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val note = getItem(position) ?: return View(context)

        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.note_item, parent, false)

        val titleTextView = view.findViewById<TextView>(R.id.titleTextView)
        val descTextView = view.findViewById<TextView>(R.id.descTextView)
        val priorityTextView = view.findViewById<TextView>(R.id.priorityTextView)
        val dateTextView = view.findViewById<TextView>(R.id.dateTextView)

        // Populate the data into the template view using the data object
        titleTextView.text = note.title
        descTextView.text = note.description
        priorityTextView.text = note.priority

        val dateFormat = SimpleDateFormat("MMM dd, yyyy - hh:mm a", Locale.getDefault())
        val date = Date(note.date.toLong())
        dateTextView.text = dateFormat.format(date)

        when (note.priority) {
            "High" -> priorityTextView.setTextColor(context.getColor(android.R.color.holo_red_dark))
            "Urgent" -> priorityTextView.setTextColor(context.getColor(android.R.color.holo_orange_dark))
            "Medium" -> priorityTextView.setTextColor(context.getColor(android.R.color.holo_orange_light))
            else -> priorityTextView.setTextColor(context.getColor(android.R.color.darker_gray))
        }

        return view
    }
}