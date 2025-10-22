package com.bookmark.app

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bookmark.app.utils.StreakManager
import com.bookmark.app.utils.UserPreferences
import com.bookmark.app.viewmodel.BookViewModel
import com.bookmark.app.viewmodel.ReadingSessionViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class BookDetailPageActivity : AppCompatActivity() {

    private lateinit var ivBookCover: ImageView
    private lateinit var tvBookTitle: TextView
    private lateinit var tvSubtitle: TextView
    private lateinit var tvAuthor: TextView
    private lateinit var tvPageCount: TextView
    private lateinit var btnAddPages: ImageButton
    private lateinit var progressBar: ProgressBar
    private lateinit var dailyStreakView: CircularStreakView
    private lateinit var writingStreakView: CircularStreakView
    
    private lateinit var bookViewModel: BookViewModel
    private lateinit var sessionViewModel: ReadingSessionViewModel
    private lateinit var userPrefs: UserPreferences
    
    private var bookId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail_page)
        
        bookId = intent.getLongExtra("BOOK_ID", -1)
        if (bookId == -1L) {
            finish()
            return
        }
        
        userPrefs = UserPreferences(this)
        
        initViews()
        setupViewModels()
        observeData()
        setupClickListeners()
    }

    private fun initViews() {
        ivBookCover = findViewById(R.id.ivBookCover)
        tvBookTitle = findViewById(R.id.tvBookTitle)
        tvSubtitle = findViewById(R.id.tvSubtitle)
        tvAuthor = findViewById(R.id.tvAuthor)
        tvPageCount = findViewById(R.id.tvPageCount)
        btnAddPages = findViewById(R.id.btnAddPages)
        progressBar = findViewById(R.id.progressBar)
        dailyStreakView = findViewById(R.id.dailyStreakView)
        writingStreakView = findViewById(R.id.writingStreakView)
    }
    
    private fun setupViewModels() {
        bookViewModel = ViewModelProvider(this)[BookViewModel::class.java]
        sessionViewModel = ViewModelProvider(this)[ReadingSessionViewModel::class.java]
    }

    private fun observeData() {
        // Observe book data
        bookViewModel.getBookById(bookId).observe(this) { book ->
            if (book != null) {
                displayBookInfo(book)
            }
        }
        
        // Observe reading sessions for streak calculation
        sessionViewModel.getSessionsForBook(bookId).observe(this) { sessions ->
            calculateAndDisplayStreaks(sessions)
        }
    }
    
    private fun displayBookInfo(book: Book) {
        tvBookTitle.text = book.title
        tvSubtitle.text = book.subtitle
        tvAuthor.text = book.author
        tvPageCount.text = book.getProgressText()
        progressBar.progress = book.getProgressPercentage()
        
        // Load book cover
        if (book.coverUrl.isNotEmpty()) {
            Glide.with(this)
                .load(book.coverUrl)
                .placeholder(R.color.surface_light)
                .into(ivBookCover)
        }
    }
    
    private fun calculateAndDisplayStreaks(sessions: List<ReadingSession>) {
        lifecycleScope.launch {
            // Get unique dates
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val dates = sessions.map { 
                dateFormat.format(Date(it.date))
            }.distinct()
            
            // Calculate streaks
            val streaks = StreakManager.calculateReadingStreak(dates)
            
            // Display daily streak
            val dailyProgress = if (streaks.daily > 0) (streaks.daily % 7) / 7f else 0f
            dailyStreakView.setStreakData(
                StreakManager.getStreakText("D", streaks.daily),
                dailyProgress,
                R.color.streak_red
            )
            
            // Display writing streak (placeholder for now)
            writingStreakView.setStreakData(
                StreakManager.getStreakText("D", 0),
                0f,
                R.color.streak_green
            )
        }
    }

    private fun setupClickListeners() {
        btnAddPages.setOnClickListener {
            showUpdateProgressDialog()
        }
    }
    
    private fun showUpdateProgressDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Update Reading Progress")
        
        val input = android.widget.EditText(this)
        input.hint = "Enter current page"
        input.inputType = android.text.InputType.TYPE_CLASS_NUMBER
        builder.setView(input)
        
        builder.setPositiveButton("Update") { _, _ ->
            val pageText = input.text.toString()
            if (pageText.isNotEmpty()) {
                val page = pageText.toIntOrNull()
                if (page != null && page > 0) {
                    updateProgress(page)
                }
            }
        }
        
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        
        builder.show()
    }
    
    private fun updateProgress(newPage: Int) {
        lifecycleScope.launch {
            // Get current book
            val book = bookViewModel.getBookById(bookId).value
            if (book != null) {
                // Update book progress
                bookViewModel.updateProgress(bookId, newPage)
                
                // Create reading session
                val session = ReadingSession(
                    bookId = bookId,
                    pagesRead = newPage - book.currentPage,
                    startPage = book.currentPage,
                    endPage = newPage,
                    userId = userPrefs.userId
                )
                sessionViewModel.insertSession(session)
                
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Progress updated to page $newPage",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }
}
