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
import com.google.android.material.floatingactionbutton.FloatingActionButton

class NowReadingActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fabAddBook: FloatingActionButton
    private lateinit var btnAdd: ImageButton
    private lateinit var btnMore: ImageButton
    private lateinit var btnNavReading: ImageButton
    private lateinit var btnNavMenu: ImageButton
    
    private lateinit var bookAdapter: BookAdapter
    private lateinit var bookViewModel: BookViewModel
    private lateinit var userPrefs: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_now_reading)
        
        userPrefs = UserPreferences(this)
        
        // Check if user is logged in
        if (!userPrefs.isLoggedIn) {
            navigateToLogin()
            return
        }
        
        initViews()
        setupViewModel()
        setupRecyclerView()
        setupClickListeners()
        observeData()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerViewCurrentBooks)
        fabAddBook = findViewById(R.id.fabAddBook)
        btnAdd = findViewById(R.id.btnAdd)
        btnMore = findViewById(R.id.btnMore)
        btnNavReading = findViewById(R.id.btnNavReading)
        btnNavMenu = findViewById(R.id.btnNavMenu)
    }
    
    private fun setupViewModel() {
        bookViewModel = ViewModelProvider(this)[BookViewModel::class.java]
    }

    private fun setupRecyclerView() {
        bookAdapter = BookAdapter { book ->
            // Navigate to Book Detail
            val intent = Intent(this, BookDetailPageActivity::class.java)
            intent.putExtra("BOOK_ID", book.id)
            startActivity(intent)
        }
        
        recyclerView.apply {
            adapter = bookAdapter
            layoutManager = LinearLayoutManager(this@NowReadingActivity)
        }
    }

    private fun setupClickListeners() {
        btnAdd.setOnClickListener {
            // Navigate to Add Bookmark flow
            startActivity(Intent(this, AddBookmarkActivity::class.java))
        }

        btnMore.setOnClickListener {
            // Navigate to More Options
            startActivity(Intent(this, MoreOptionsActivity::class.java))
        }
        
        btnNavMenu.setOnClickListener {
            // Navigate to More Options from bottom nav
            startActivity(Intent(this, MoreOptionsActivity::class.java))
        }
        
        btnNavReading.setOnClickListener {
            // Already on Reading tab - do nothing or refresh
        }
        
        fabAddBook.setOnClickListener {
            // Quick add book
            startActivity(Intent(this, AddBookmarkActivity::class.java))
        }
    }

    private fun observeData() {
        // Observe currently reading books
        bookViewModel.getCurrentlyReadingBooks(userPrefs.userId).observe(this) { books ->
            if (books.isEmpty()) {
                // Show empty state or create sample books for first-time users
                if (userPrefs.isFirstTime) {
                    createSampleBooks()
                    userPrefs.isFirstTime = false
                }
            } else {
                bookAdapter.submitList(books)
            }
        }
    }
    
    private fun createSampleBooks() {
        // Create a couple of sample books for new users
        val sampleBook1 = Book(
            title = "Mahagatha",
            subtitle = "100 Tales from the Puranas",
            author = "Satyarth Nayak",
            currentPage = 30,
            totalPages = 300,
            userId = userPrefs.userId
        )
        
        bookViewModel.insertBook(sampleBook1)
    }
    
    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        // Data will auto-refresh via LiveData
    }
}
