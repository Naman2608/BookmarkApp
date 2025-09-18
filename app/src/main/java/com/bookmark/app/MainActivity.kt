package com.bookmark.app

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var booksRecyclerView: RecyclerView
    private lateinit var btnAddBook: Button
    private lateinit var emptyStateLayout: View
    
    private lateinit var bookAdapter: BookAdapter
    private var allBooks = mutableListOf<Book>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupRecyclerView()
        setupAddBookButton()
        loadSampleData()
    }

    private fun initViews() {
        booksRecyclerView = findViewById(R.id.booksRecyclerView)
        btnAddBook = findViewById(R.id.btnAddBook)
        emptyStateLayout = findViewById(R.id.emptyStateLayout)
    }

    private fun setupRecyclerView() {
        bookAdapter = BookAdapter(
            books = allBooks,
            onBookClick = { book ->
                Toast.makeText(this, "Opening: ${book.title}", Toast.LENGTH_SHORT).show()
            },
            onAddPagesClick = { book ->
                // Handle add pages functionality
                addPagesToBook(book)
            }
        )
        
        booksRecyclerView.apply {
            adapter = bookAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    private fun setupAddBookButton() {
        btnAddBook.setOnClickListener {
            Toast.makeText(this, "Add book feature coming soon!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addPagesToBook(book: Book) {
        // For demo purposes, add 10 pages
        val updatedBook = book.copy(currentPage = book.currentPage + 10)
        val index = allBooks.indexOf(book)
        if (index != -1) {
            allBooks[index] = updatedBook
            bookAdapter.notifyItemChanged(index)
        }
        Toast.makeText(this, "Added 10 pages to ${book.title}", Toast.LENGTH_SHORT).show()
    }

    private fun updateEmptyState() {
        if (allBooks.isEmpty()) {
            booksRecyclerView.visibility = View.GONE
            emptyStateLayout.visibility = View.VISIBLE
        } else {
            booksRecyclerView.visibility = View.VISIBLE
            emptyStateLayout.visibility = View.GONE
        }
    }

    private fun loadSampleData() {
        allBooks.addAll(listOf(
            Book(
                id = 1,
                title = "Vyaktitva ka Vikas",
                currentPage = 90,
                totalPages = 225,
                currentChapter = "",
                progressColor = "#E91E63" // Purple
            ),
            Book(
                id = 2,
                title = "Almanac of Naval Ravikant",
                currentPage = 90,
                totalPages = 225,
                currentChapter = "Part - 2",
                progressColor = "#000000" // Black
            ),
            Book(
                id = 3,
                title = "Creative Confidence",
                currentPage = 50,
                totalPages = 300,
                currentChapter = "Chapter 2",
                progressColor = "#4CAF50" // Green
            ),
            Book(
                id = 4,
                title = "How to Talk",
                currentPage = 0,
                totalPages = 200,
                currentChapter = "Preface",
                progressColor = "#E0E0E0" // Gray (not started)
            ),
            Book(
                id = 5,
                title = "Creative Confidence",
                currentPage = 100,
                totalPages = 350,
                currentChapter = "Chapter 3",
                progressColor = "#4CAF50" // Green
            ),
            Book(
                id = 6,
                title = "The Design of Everyday things",
                currentPage = 60,
                totalPages = 500,
                currentChapter = "Chapter 2",
                progressColor = "#FF9800" // Orange
            ),
            Book(
                id = 7,
                title = "Elon Musk",
                currentPage = 70,
                totalPages = 1050,
                currentChapter = "Chapter 3",
                progressColor = "#F44336" // Red
            ),
            Book(
                id = 8,
                title = "Operating Systems",
                currentPage = 70,
                totalPages = 1050,
                currentChapter = "Chapter 3",
                progressColor = "#2196F3", // Blue
                isCompleted = true
            )
        ))

        updateEmptyState()
        bookAdapter.notifyDataSetChanged()
    }
}