package com.bookmark.app

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bookmark.app.utils.UserPreferences
import com.bookmark.app.viewmodel.NoteViewModel
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class NotesActivity : AppCompatActivity() {

    private lateinit var btnBack: ImageButton
    private lateinit var btnSkip: TextView
    private lateinit var btnSave: ImageButton
    private lateinit var tvTimestamp: TextView
    private lateinit var etNoteContent: EditText
    
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var userPrefs: UserPreferences
    
    private var bookId: Long = -1
    private var bookTitle: String = ""
    private var noteId: Long = -1
    private var existingNote: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        
        bookId = intent.getLongExtra("BOOK_ID", -1)
        bookTitle = intent.getStringExtra("BOOK_TITLE") ?: "Note"
        noteId = intent.getLongExtra("NOTE_ID", -1)
        
        if (bookId == -1L) {
            finish()
            return
        }
        
        userPrefs = UserPreferences(this)
        
        initViews()
        setupViewModel()
        setupClickListeners()
        loadNote()
    }

    private fun initViews() {
        btnBack = findViewById(R.id.btnBack)
        btnSkip = findViewById(R.id.btnSkip)
        btnSave = findViewById(R.id.btnSave)
        tvTimestamp = findViewById(R.id.tvTimestamp)
        etNoteContent = findViewById(R.id.etNoteContent)
        
        // Set current timestamp
        updateTimestamp()
    }
    
    private fun setupViewModel() {
        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
    }
    
    private fun updateTimestamp() {
        val dateFormat = SimpleDateFormat("d MMMM, HH:mm", Locale.getDefault())
        tvTimestamp.text = dateFormat.format(Date())
    }

    private fun loadNote() {
        if (noteId != -1L) {
            // Load existing note
            noteViewModel.getNoteById(noteId).observe(this) { note ->
                if (note != null) {
                    existingNote = note
                    etNoteContent.setText(note.content)
                }
            }
        }
    }

    private fun setupClickListeners() {
        btnBack.setOnClickListener {
            finish()
        }
        
        btnSkip.setOnClickListener {
            finish()
        }
        
        btnSave.setOnClickListener {
            saveNote()
        }
    }
    
    private fun saveNote() {
        val content = etNoteContent.text.toString().trim()
        
        if (content.isEmpty()) {
            Snackbar.make(
                findViewById(android.R.id.content),
                "Note cannot be empty",
                Snackbar.LENGTH_SHORT
            ).show()
            return
        }
        
        if (existingNote != null) {
            // Update existing note
            val updatedNote = existingNote!!.copy(
                content = content,
                dateModified = System.currentTimeMillis()
            )
            noteViewModel.updateNote(updatedNote)
            Snackbar.make(
                findViewById(android.R.id.content),
                "Note updated",
                Snackbar.LENGTH_SHORT
            ).show()
        } else {
            // Create new note
            val newNote = Note(
                bookId = bookId,
                content = content,
                userId = userPrefs.userId
            )
            noteViewModel.insertNote(newNote) { noteId ->
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Note saved",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
        
        // Close after a short delay
        etNoteContent.postDelayed({
            finish()
        }, 500)
    }
}
