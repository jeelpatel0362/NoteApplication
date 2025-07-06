package com.example.noteapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "NotesDB", null, 1) {

    companion object {
        const val TABLE_NAME = "notes"
        const val COL_ID = "id"
        const val COL_TITLE = "title"
        const val COL_DESC = "description"
        const val COL_PRIORITY = "priority"
        const val COL_DATE = "date"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_NAME (" +
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COL_TITLE TEXT, " +
                "$COL_DESC TEXT, " +
                "$COL_PRIORITY TEXT, " +
                "$COL_DATE TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addNote(title: String, description: String, priority: String): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COL_TITLE, title)
        values.put(COL_DESC, description)
        values.put(COL_PRIORITY, priority)
        values.put(COL_DATE, System.currentTimeMillis().toString())

        return db.insert(TABLE_NAME, null, values)
    }

    fun getAllNotes(): ArrayList<Note> {
        val noteList = ArrayList<Note>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                noteList.add(Note(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
                ))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return noteList
    }
    fun deleteNote(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "$COL_ID = ?", arrayOf(id.toString()))
    }

    fun updateNote(note: Note): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COL_TITLE, note.title)
            put(COL_DESC, note.description)
            put(COL_PRIORITY, note.priority)
        }
        return db.update(TABLE_NAME, values, "$COL_ID = ?", arrayOf(note.id.toString()))
    }

    fun getNoteById(id: Int): Note? {
        val db = this.readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            null,
            "$COL_ID = ?",
            arrayOf(id.toString()),
            null, null, null
        )

        return if (cursor.moveToFirst()) {
            Note(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4)
            )
        } else {
            null
        }.also { cursor.close() }
    }
}