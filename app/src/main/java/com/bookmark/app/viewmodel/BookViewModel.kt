package com.bookmark.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bookmark.app.Book
import com.bookmark.app.BookWithNotes
import com.bookmark.app.BookWithSessions
import com.bookmark.app.data.BookmarkDatabase
import com.bookmark.app.repository.BookRepository
import kotlinx.coroutines.launch

class BookViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: BookRepository
    
    init {
        val bookDao = BookmarkDatabase.getDatabase(application).bookDao()
        repository = BookRepository(bookDao)
    }
    
    fun getAllBooks(userId: String): LiveData<List<Book>> {
        return repository.getAllBooks(userId)
    }
    
    fun getCurrentlyReadingBooks(userId: String): LiveData<List<Book>> {
        return repository.getCurrentlyReadingBooks(userId)
    }
    
    fun getCompletedBooks(userId: String): LiveData<List<Book>> {
        return repository.getCompletedBooks(userId)
    }
    
    fun getBookById(bookId: Long): LiveData<Book> {
        return repository.getBookById(bookId)
    }
    
    fun getBookWithNotes(bookId: Long): LiveData<BookWithNotes> {
        return repository.getBookWithNotes(bookId)
    }
    
    fun getBookWithSessions(bookId: Long): LiveData<BookWithSessions> {
        return repository.getBookWithSessions(bookId)
    }
    
    fun insertBook(book: Book, onComplete: (Long) -> Unit = {}) {
        viewModelScope.launch {
            val bookId = repository.insertBook(book)
            onComplete(bookId)
        }
    }
    
    fun updateBook(book: Book) {
        viewModelScope.launch {
            repository.updateBook(book)
        }
    }
    
    fun deleteBook(book: Book) {
        viewModelScope.launch {
            repository.deleteBook(book)
        }
    }
    
    fun updateProgress(bookId: Long, page: Int) {
        viewModelScope.launch {
            repository.updateProgress(bookId, page)
        }
    }
    
    fun updateCompletionStatus(bookId: Long, completed: Boolean) {
        viewModelScope.launch {
            repository.updateCompletionStatus(bookId, completed)
        }
    }
    
    fun getTotalBookCount(userId: String): LiveData<Int> {
        return repository.getTotalBookCount(userId)
    }
    
    fun getCurrentlyReadingCount(userId: String): LiveData<Int> {
        return repository.getCurrentlyReadingCount(userId)
    }
}
