package com.example.noteapplication

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var notesListView: ListView
    private lateinit var addNoteButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DatabaseHelper(this)
        notesListView = findViewById(R.id.notesListView)
        addNoteButton = findViewById(R.id.addNoteButton)

        addNoteButton.setOnClickListener {
            startActivity(Intent(this, AddNoteActivity::class.java))
        }

        loadNotes()
    }

    override fun onResume() {
        super.onResume()
        loadNotes()
    }

    private fun loadNotes() {
        val notes = dbHelper.getAllNotes()

        val sortedNotes = notes.sortedByDescending { it.date.toLong() }

        val adapter = NoteAdapter(this, sortedNotes)
        notesListView.adapter = adapter

        notesListView.setOnItemClickListener { _, _, position, _ ->
            val note = sortedNotes[position]
            showNoteOptionsDialog(note)
        }
    }

    private fun showNoteOptionsDialog(note: Note) {
        val options = arrayOf("View Note", "Edit Note", "Delete Note")

        AlertDialog.Builder(this)
            .setTitle("Note Options")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> showNoteDetails(note)
                    1 -> editNote(note)
                    2 -> deleteNote(note)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showNoteDetails(note: Note) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.note_detail_dialog, null)

        dialogView.findViewById<TextView>(R.id.dialogTitle).text = note.title
        dialogView.findViewById<TextView>(R.id.dialogDescription).text = note.description
        dialogView.findViewById<TextView>(R.id.dialogPriority).text = "Priority: ${note.priority}"

        val dateFormat = SimpleDateFormat("MMM dd, yyyy - hh:mm a", Locale.getDefault())
        val date = Date(note.date.toLong())
        dialogView.findViewById<TextView>(R.id.dialogDate).text =
            "Created: ${dateFormat.format(date)}"

        AlertDialog.Builder(this)
            .setView(dialogView)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun editNote(note: Note) {
        val intent = Intent(this, AddNoteActivity::class.java).apply {
            putExtra("NOTE_ID", note.id)
            putExtra("NOTE_TITLE", note.title)
            putExtra("NOTE_DESC", note.description)
            putExtra("NOTE_PRIORITY", note.priority)
        }
        startActivity(intent)
    }

    private fun deleteNote(note: Note) {
        AlertDialog.Builder(this)
            .setTitle("Delete Note")
            .setMessage("Are you sure you want to delete '${note.title}'?")
            .setPositiveButton("Delete") { _, _ ->
                dbHelper.deleteNote(note.id)
                loadNotes() // Refresh the list
                Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}