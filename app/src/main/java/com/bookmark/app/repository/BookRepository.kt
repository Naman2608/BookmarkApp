package com.bookmark.app.repository

import androidx.lifecycle.LiveData
import com.bookmark.app.Book
import com.bookmark.app.BookWithNotes
import com.bookmark.app.BookWithSessions
import com.bookmark.app.data.BookDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BookRepository(private val bookDao: BookDao) {
    
    fun getAllBooks(userId: String): LiveData<List<Book>> {
        return bookDao.getAllBooks(userId)
    }
    
    fun getCurrentlyReadingBooks(userId: String): LiveData<List<Book>> {
        return bookDao.getCurrentlyReadingBooks(userId)
    }
    
    fun getCompletedBooks(userId: String): LiveData<List<Book>> {
        return bookDao.getCompletedBooks(userId)
    }
    
    fun getBookById(bookId: Long): LiveData<Book> {
        return bookDao.getBookById(bookId)
    }
    
    fun getBookWithNotes(bookId: Long): LiveData<BookWithNotes> {
        return bookDao.getBookWithNotes(bookId)
    }
    
    fun getBookWithSessions(bookId: Long): LiveData<BookWithSessions> {
        return bookDao.getBookWithSessions(bookId)
    }
    
    suspend fun insertBook(book: Book): Long {
        return withContext(Dispatchers.IO) {
            bookDao.insertBook(book)
        }
    }
    
    suspend fun updateBook(book: Book) {
        withContext(Dispatchers.IO) {
            bookDao.updateBook(book)
        }
    }
    
    suspend fun deleteBook(book: Book) {
        withContext(Dispatchers.IO) {
            bookDao.deleteBook(book)
        }
    }
    
    suspend fun updateProgress(bookId: Long, page: Int) {
        withContext(Dispatchers.IO) {
            bookDao.updateProgress(bookId, page, System.currentTimeMillis())
        }
    }
    
    suspend fun updateCompletionStatus(bookId: Long, completed: Boolean) {
        withContext(Dispatchers.IO) {
            bookDao.updateCompletionStatus(bookId, completed)
        }
    }
    
    fun getTotalBookCount(userId: String): LiveData<Int> {
        return bookDao.getTotalBookCount(userId)
    }
    
    fun getCurrentlyReadingCount(userId: String): LiveData<Int> {
        return bookDao.getCurrentlyReadingCount(userId)
    }
}
