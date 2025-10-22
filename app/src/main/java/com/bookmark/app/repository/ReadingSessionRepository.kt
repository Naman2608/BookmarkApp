package com.bookmark.app.repository

import androidx.lifecycle.LiveData
import com.bookmark.app.ReadingSession
import com.bookmark.app.data.ReadingSessionDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReadingSessionRepository(private val sessionDao: ReadingSessionDao) {
    
    fun getSessionsForBook(bookId: Long): LiveData<List<ReadingSession>> {
        return sessionDao.getSessionsForBook(bookId)
    }
    
    fun getRecentSessions(userId: String): LiveData<List<ReadingSession>> {
        return sessionDao.getRecentSessions(userId)
    }
    
    fun getSessionsSince(userId: String, startDate: Long): LiveData<List<ReadingSession>> {
        return sessionDao.getSessionsSince(userId, startDate)
    }
    
    suspend fun getUniqueDatesWithSessions(userId: String, startDate: Long): List<String> {
        return withContext(Dispatchers.IO) {
            sessionDao.getUniqueDatesWithSessions(userId, startDate)
        }
    }
    
    suspend fun insertSession(session: ReadingSession): Long {
        return withContext(Dispatchers.IO) {
            sessionDao.insertSession(session)
        }
    }
    
    suspend fun updateSession(session: ReadingSession) {
        withContext(Dispatchers.IO) {
            sessionDao.updateSession(session)
        }
    }
    
    suspend fun deleteSession(session: ReadingSession) {
        withContext(Dispatchers.IO) {
            sessionDao.deleteSession(session)
        }
    }
    
    fun getTotalPagesRead(userId: String): LiveData<Int> {
        return sessionDao.getTotalPagesRead(userId)
    }
    
    fun getReadingDaysCount(userId: String, startDate: Long): LiveData<Int> {
        return sessionDao.getReadingDaysCount(userId, startDate)
    }
}
