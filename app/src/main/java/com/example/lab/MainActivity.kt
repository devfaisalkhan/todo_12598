package com.example.lab

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

data class Note(val title: String, val description: String, val category: String)

class MainActivity : AppCompatActivity() {
    private lateinit var notesContainer: LinearLayout
    private lateinit var addNoteButton: Button
    private val notesList = ArrayList<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notesContainer = findViewById(R.id.notesContainer)
        val addNoteFab = findViewById<FloatingActionButton>(R.id.addNoteButton) // Correct cast

        // Predefined list of notes (todos)
        notesList.add(Note("Meeting with team", "Discuss the project status and tasks.", "Work"))
        notesList.add(Note("Buy groceries", "Milk, eggs, and bread.", "Personal"))
        notesList.add(Note("Doctor's appointment", "Check-up at the clinic.", "Health"))
        notesList.add(Note("Workout session", "Leg day at the gym.", "Fitness"))

        // Display the predefined notes on startup
        displayNotes()

        addNoteFab.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivityForResult(intent, 1) // Start AddNoteActivity for result
        }
    }

    // Handle returning data from AddNoteActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val title = data?.getStringExtra("title") ?: "" // Retrieve title
            val description = data?.getStringExtra("description") ?: "" // Retrieve description
            val category = data?.getStringExtra("category") ?: "" // Retrieve category

            if (title.isNotEmpty() && description.isNotEmpty() && category.isNotEmpty()) {
                notesList.add(Note(title, description, category)) // Add the note with category
                displayNotes() // Display the updated list of notes
            } else {
                Toast.makeText(this, "Note data is missing", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Display the list of notes
    private fun displayNotes() {
        notesContainer.removeAllViews()
        for (note in notesList) {
            val noteView = layoutInflater.inflate(R.layout.activity_item_note, notesContainer, false)
            val titleText = noteView.findViewById<TextView>(R.id.noteTitle)
            val descriptionText = noteView.findViewById<TextView>(R.id.noteDescription)
            val categoryText = noteView.findViewById<TextView>(R.id.noteCategory)
            val deleteButton = noteView.findViewById<Button>(R.id.deleteNoteButton)

            titleText.text = note.title
            descriptionText.text = note.description
            categoryText.text = note.category

            deleteButton.setOnClickListener {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Delete Note")
                builder.setMessage("Are you sure you want to delete this note?")

                // If the user clicks "Yes"
                builder.setPositiveButton("Yes") { dialog, which ->
                    notesList.remove(note)
                    displayNotes() // Update the displayed notes list
                    Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show()
                }

                // If the user clicks "No"
                builder.setNegativeButton("No") { dialog, which ->
                    // Do nothing and dismiss the dialog
                    dialog.dismiss()
                }
                builder.create().show()
            }

            notesContainer.addView(noteView)
        }
    }
}
