package com.bookmark.app.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bookmark.app.Book
import com.bookmark.app.BookWithNotes
import com.bookmark.app.BookWithSessions

@Dao
interface BookDao {
    
    @Query("SELECT * FROM books WHERE userId = :userId ORDER BY lastReadDate DESC")
    fun getAllBooks(userId: String): LiveData<List<Book>>
    
    @Query("SELECT * FROM books WHERE userId = :userId AND isCompleted = 0 ORDER BY lastReadDate DESC")
    fun getCurrentlyReadingBooks(userId: String): LiveData<List<Book>>
    
    @Query("SELECT * FROM books WHERE userId = :userId AND isCompleted = 1 ORDER BY dateAdded DESC")
    fun getCompletedBooks(userId: String): LiveData<List<Book>>
    
    @Query("SELECT * FROM books WHERE id = :bookId")
    fun getBookById(bookId: Long): LiveData<Book>
    
    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookByIdSync(bookId: Long): Book?
    
    @Transaction
    @Query("SELECT * FROM books WHERE id = :bookId")
    fun getBookWithNotes(bookId: Long): LiveData<BookWithNotes>
    
    @Transaction
    @Query("SELECT * FROM books WHERE id = :bookId")
    fun getBookWithSessions(bookId: Long): LiveData<BookWithSessions>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Book): Long
    
    @Update
    suspend fun updateBook(book: Book)
    
    @Delete
    suspend fun deleteBook(book: Book)
    
    @Query("DELETE FROM books WHERE id = :bookId")
    suspend fun deleteBookById(bookId: Long)
    
    @Query("UPDATE books SET currentPage = :page, lastReadDate = :date WHERE id = :bookId")
    suspend fun updateProgress(bookId: Long, page: Int, date: Long)
    
    @Query("UPDATE books SET isCompleted = :completed WHERE id = :bookId")
    suspend fun updateCompletionStatus(bookId: Long, completed: Boolean)
    
    @Query("SELECT COUNT(*) FROM books WHERE userId = :userId")
    fun getTotalBookCount(userId: String): LiveData<Int>
    
    @Query("SELECT COUNT(*) FROM books WHERE userId = :userId AND isCompleted = 0")
    fun getCurrentlyReadingCount(userId: String): LiveData<Int>
}
