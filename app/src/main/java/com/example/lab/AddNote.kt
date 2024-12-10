package com.example.lab

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity

class AddNoteActivity : AppCompatActivity() {
    private lateinit var noteTitleInput: EditText
    private lateinit var noteDescriptionInput: EditText
    private lateinit var noteCategorySpinner: Spinner
    private lateinit var saveNoteButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        noteTitleInput = findViewById(R.id.noteTitleInput)
        noteDescriptionInput = findViewById(R.id.noteDescriptionInput)
        noteCategorySpinner = findViewById(R.id.noteCategorySpinner)
        saveNoteButton = findViewById(R.id.saveNoteButton)

        val categories = arrayOf("Work", "Personal", "Health", "Finance", "Family", "Hobbies", "Education", "Travel", "Shopping", "Others")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        noteCategorySpinner.adapter = adapter

        // Save the note when the button is clicked
        saveNoteButton.setOnClickListener {
            val title = noteTitleInput.text.toString()
            val description = noteDescriptionInput.text.toString()
            val category = noteCategorySpinner.selectedItem.toString()

            val resultIntent = Intent()
            resultIntent.putExtra("title", title)
            resultIntent.putExtra("description", description)
            resultIntent.putExtra("category", category)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}
