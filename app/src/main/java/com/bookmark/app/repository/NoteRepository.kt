package com.bookmark.app.repository

import androidx.lifecycle.LiveData
import com.bookmark.app.Note
import com.bookmark.app.data.NoteDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NoteRepository(private val noteDao: NoteDao) {
    
    fun getNotesForBook(bookId: Long): LiveData<List<Note>> {
        return noteDao.getNotesForBook(bookId)
    }
    
    fun getAllNotes(userId: String): LiveData<List<Note>> {
        return noteDao.getAllNotes(userId)
    }
    
    fun getNoteById(noteId: Long): LiveData<Note> {
        return noteDao.getNoteById(noteId)
    }
    
    fun getNoteCountForBook(bookId: Long): LiveData<Int> {
        return noteDao.getNoteCountForBook(bookId)
    }
    
    suspend fun insertNote(note: Note): Long {
        return withContext(Dispatchers.IO) {
            noteDao.insertNote(note)
        }
    }
    
    suspend fun updateNote(note: Note) {
        withContext(Dispatchers.IO) {
            noteDao.updateNote(note)
        }
    }
    
    suspend fun deleteNote(note: Note) {
        withContext(Dispatchers.IO) {
            noteDao.deleteNote(note)
        }
    }
}
