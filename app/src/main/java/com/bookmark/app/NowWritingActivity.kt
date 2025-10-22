package com.bookmark.app

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bookmark.app.utils.UserPreferences
import com.bookmark.app.viewmodel.BookViewModel
import com.bookmark.app.viewmodel.NoteViewModel

class NowWritingActivity : AppCompatActivity() {

    private lateinit var rvNotebooks: RecyclerView
    private lateinit var btnAddNotebook: ImageButton
    private lateinit var btnMoreOptions: ImageButton
    private lateinit var btnReadingTab: ImageButton
    
    private lateinit var notebookAdapter: NotebookAdapter
    private lateinit var bookViewModel: BookViewModel
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var userPrefs: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_now_writing)
        
        userPrefs = UserPreferences(this)
        
        initViews()
        setupViewModels()
        setupRecyclerView()
        setupClickListeners()
        observeData()
    }

    private fun initViews() {
        rvNotebooks = findViewById(R.id.rvNotebooks)
        btnAddNotebook = findViewById(R.id.btnAddNotebook)
        btnMoreOptions = findViewById(R.id.btnMoreOptions)
        btnReadingTab = findViewById(R.id.btnReadingTab)
    }
    
    private fun setupViewModels() {
        bookViewModel = ViewModelProvider(this)[BookViewModel::class.java]
        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]
    }

    private fun setupRecyclerView() {
        notebookAdapter = NotebookAdapter { book ->
            // Navigate to notes page for this book
            val intent = Intent(this, NotesActivity::class.java)
            intent.putExtra("BOOK_ID", book.id)
            intent.putExtra("BOOK_TITLE", book.title)
            startActivity(intent)
        }
        
        rvNotebooks.apply {
            adapter = notebookAdapter
            layoutManager = LinearLayoutManager(this@NowWritingActivity)
        }
    }

    private fun setupClickListeners() {
        btnAddNotebook.setOnClickListener {
            // Navigate to add book (which creates a new notebook)
            startActivity(Intent(this, AddBookmarkActivity::class.java))
        }
        
        btnMoreOptions.setOnClickListener {
            startActivity(Intent(this, MoreOptionsActivity::class.java))
        }
        
        btnReadingTab.setOnClickListener {
            // Go back to reading tab
            finish() // Returns to NowReadingActivity
        }
    }

    private fun observeData() {
        // Get all books for the user
        val userId = userPrefs.userId
        
        bookViewModel.getAllBooks(userId).observe(this) { books ->
            if (books.isNotEmpty()) {
                // For each book, get its notes and create BookWithNotes
                val booksWithNotes = mutableListOf<BookWithNotes>()
                
                books.forEach { book ->
                    noteViewModel.getNotesForBook(book.id).observe(this) { notes ->
                        val bookWithNotes = BookWithNotes(book, notes)
                        
                        // Remove if already exists (to avoid duplicates)
                        booksWithNotes.removeAll { it.book.id == book.id }
                        booksWithNotes.add(bookWithNotes)
                        
                        // Update adapter
                        notebookAdapter.submitList(booksWithNotes.toList())
                    }
                }
            } else {
                // Show empty state
                notebookAdapter.submitList(emptyList())
            }
        }
    }
}
