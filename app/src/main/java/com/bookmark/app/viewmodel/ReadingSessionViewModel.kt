package com.bookmark.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bookmark.app.ReadingSession
import com.bookmark.app.data.BookmarkDatabase
import com.bookmark.app.repository.ReadingSessionRepository
import kotlinx.coroutines.launch

class ReadingSessionViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: ReadingSessionRepository
    
    init {
        val sessionDao = BookmarkDatabase.getDatabase(application).readingSessionDao()
        repository = ReadingSessionRepository(sessionDao)
    }
    
    fun getSessionsForBook(bookId: Long): LiveData<List<ReadingSession>> {
        return repository.getSessionsForBook(bookId)
    }
    
    fun getRecentSessions(userId: String): LiveData<List<ReadingSession>> {
        return repository.getRecentSessions(userId)
    }
    
    fun getSessionsSince(userId: String, startDate: Long): LiveData<List<ReadingSession>> {
        return repository.getSessionsSince(userId, startDate)
    }
    
    fun insertSession(session: ReadingSession, onComplete: (Long) -> Unit = {}) {
        viewModelScope.launch {
            val sessionId = repository.insertSession(session)
            onComplete(sessionId)
        }
    }
    
    fun updateSession(session: ReadingSession) {
        viewModelScope.launch {
            repository.updateSession(session)
        }
    }
    
    fun deleteSession(session: ReadingSession) {
        viewModelScope.launch {
            repository.deleteSession(session)
        }
    }
    
    fun getTotalPagesRead(userId: String): LiveData<Int> {
        return repository.getTotalPagesRead(userId)
    }
    
    fun getReadingDaysCount(userId: String, startDate: Long): LiveData<Int> {
        return repository.getReadingDaysCount(userId, startDate)
    }
}
