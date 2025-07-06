package com.example.noteapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddNoteActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var titleEditText: EditText
    private lateinit var descEditText: EditText
    private lateinit var prioritySpinner: Spinner
    private lateinit var saveButton: Button
    private var noteId: Int = -1
    private val priorities = arrayOf("High", "Urgent", "Medium", "Normal")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        dbHelper = DatabaseHelper(this)
        initViews()
        setupPrioritySpinner()
        checkEditMode()
        setupSaveButton()
    }

    private fun initViews() {
        titleEditText = findViewById(R.id.titleEditText)
        descEditText = findViewById(R.id.descEditText)
        prioritySpinner = findViewById(R.id.prioritySpinner)
        saveButton = findViewById(R.id.saveButton)
    }

    private fun setupPrioritySpinner() {
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            priorities
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        prioritySpinner.adapter = adapter
    }

    private fun checkEditMode() {
        noteId = intent.getIntExtra("NOTE_ID", -1)
        if (noteId != -1) {
            loadNoteData()
            saveButton.text = getString(R.string.update_note)
        }
    }

    private fun loadNoteData() {
        val note = dbHelper.getNoteById(noteId) ?: run {
            Toast.makeText(this, "Note not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        titleEditText.setText(note.title)
        descEditText.setText(note.description)

        val priorityPosition = priorities.indexOf(note.priority)
        if (priorityPosition >= 0) {
            prioritySpinner.setSelection(priorityPosition)
        }
    }

    private fun setupSaveButton() {
        saveButton.setOnClickListener {
            if (validateInput()) {
                saveOrUpdateNote()
            }
        }
    }

    private fun validateInput(): Boolean {
        return when {
            titleEditText.text.isNullOrBlank() -> {
                titleEditText.error = getString(R.string.title_required)
                false
            }
            else -> true
        }
    }

    private fun saveOrUpdateNote() {
        val title = titleEditText.text.toString().trim()
        val description = descEditText.text.toString().trim()
        val priority = prioritySpinner.selectedItem.toString()
        val timestamp = System.currentTimeMillis().toString()

        if (noteId == -1) {
            val insertedId = dbHelper.addNote(title, description, priority)
            if (insertedId != -1L) {
                showSuccessMessage(getString(R.string.note_saved))
                finish()
            } else {
                showErrorMessage(getString(R.string.save_failed))
            }
        } else {
            val note = Note(noteId, title, description, priority, timestamp)
            val rowsAffected = dbHelper.updateNote(note)
            if (rowsAffected > 0) {
                showSuccessMessage(getString(R.string.note_updated))
                finish()
            } else {
                showErrorMessage(getString(R.string.update_failed))
            }
        }
    }

    private fun showSuccessMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun startForNewNote(context: Context) {
            val intent = Intent(context, AddNoteActivity::class.java)
            context.startActivity(intent)
        }

        fun startForEditNote(context: Context, noteId: Int) {
            val intent = Intent(context, AddNoteActivity::class.java).apply {
                putExtra("NOTE_ID", noteId)
            }
            context.startActivity(intent)
        }
    }
}