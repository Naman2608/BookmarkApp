package com.bookmark.app.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bookmark.app.ReadingSession

@Dao
interface ReadingSessionDao {
    
    @Query("SELECT * FROM reading_sessions WHERE bookId = :bookId ORDER BY date DESC")
    fun getSessionsForBook(bookId: Long): LiveData<List<ReadingSession>>
    
    @Query("SELECT * FROM reading_sessions WHERE userId = :userId ORDER BY date DESC LIMIT 30")
    fun getRecentSessions(userId: String): LiveData<List<ReadingSession>>
    
    @Query("SELECT * FROM reading_sessions WHERE userId = :userId AND date >= :startDate ORDER BY date DESC")
    fun getSessionsSince(userId: String, startDate: Long): LiveData<List<ReadingSession>>
    
    @Query("SELECT DISTINCT date(date/1000, 'unixepoch') as day FROM reading_sessions WHERE userId = :userId AND date >= :startDate")
    suspend fun getUniqueDatesWithSessions(userId: String, startDate: Long): List<String>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: ReadingSession): Long
    
    @Update
    suspend fun updateSession(session: ReadingSession)
    
    @Delete
    suspend fun deleteSession(session: ReadingSession)
    
    @Query("DELETE FROM reading_sessions WHERE bookId = :bookId")
    suspend fun deleteSessionsForBook(bookId: Long)
    
    @Query("SELECT SUM(pagesRead) FROM reading_sessions WHERE userId = :userId")
    fun getTotalPagesRead(userId: String): LiveData<Int>
    
    @Query("SELECT COUNT(DISTINCT date(date/1000, 'unixepoch')) FROM reading_sessions WHERE userId = :userId AND date >= :startDate")
    fun getReadingDaysCount(userId: String, startDate: Long): LiveData<Int>
}
