package com.bookmark.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bookmark.app.utils.UserPreferences
import com.bookmark.app.viewmodel.BookViewModel
import com.google.android.material.tabs.TabLayout

class BooklistActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var recyclerViewBooks: RecyclerView
    private lateinit var bookAdapter: BookAdapter
    private lateinit var bookViewModel: BookViewModel
    private lateinit var userPrefs: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booklist)
        
        userPrefs = UserPreferences(this)
        
        initViews()
        setupViewModel()
        setupTabs()
        setupRecyclerView()
    }

    private fun initViews() {
        tabLayout = findViewById(R.id.tabLayout)
        recyclerViewBooks = findViewById(R.id.recyclerViewBooks)
    }
    
    private fun setupViewModel() {
        bookViewModel = ViewModelProvider(this)[BookViewModel::class.java]
    }

    private fun setupTabs() {
        tabLayout.addTab(tabLayout.newTab().setText("All Books"))
        tabLayout.addTab(tabLayout.newTab().setText("Currently Reading"))
        tabLayout.addTab(tabLayout.newTab().setText("Completed"))
        
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> showAllBooks()
                    1 -> showCurrentBooks()
                    2 -> showCompletedBooks()
                }
            }
            
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun setupRecyclerView() {
        bookAdapter = BookAdapter { book ->
            // Navigate to book detail page
            val intent = Intent(this, BookDetailPageActivity::class.java)
            intent.putExtra("BOOK_ID", book.id)
            startActivity(intent)
        }
        
        recyclerViewBooks.apply {
            adapter = bookAdapter
            layoutManager = LinearLayoutManager(this@BooklistActivity)
        }
        
        // Load all books by default
        showAllBooks()
    }

    private fun showAllBooks() {
        bookViewModel.getAllBooks(userPrefs.userId).observe(this) { books ->
            bookAdapter.submitList(books)
        }
    }

    private fun showCurrentBooks() {
        bookViewModel.getCurrentlyReadingBooks(userPrefs.userId).observe(this) { books ->
            bookAdapter.submitList(books)
        }
    }

    private fun showCompletedBooks() {
        bookViewModel.getCompletedBooks(userPrefs.userId).observe(this) { books ->
            bookAdapter.submitList(books)
        }
    }
}
