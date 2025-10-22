package com.bookmark.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.bookmark.app.Note
import com.bookmark.app.data.BookmarkDatabase
import com.bookmark.app.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: NoteRepository
    
    init {
        val noteDao = BookmarkDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(noteDao)
    }
    
    fun getNotesForBook(bookId: Long): LiveData<List<Note>> {
        return repository.getNotesForBook(bookId)
    }
    
    fun getAllNotes(userId: String): LiveData<List<Note>> {
        return repository.getAllNotes(userId)
    }
    
    fun getNoteById(noteId: Long): LiveData<Note> {
        return repository.getNoteById(noteId)
    }
    
    fun getNoteCountForBook(bookId: Long): LiveData<Int> {
        return repository.getNoteCountForBook(bookId)
    }
    
    fun insertNote(note: Note, onComplete: (Long) -> Unit = {}) {
        viewModelScope.launch {
            val noteId = repository.insertNote(note)
            onComplete(noteId)
        }
    }
    
    fun updateNote(note: Note) {
        viewModelScope.launch {
            repository.updateNote(note)
        }
    }
    
    fun deleteNote(note: Note) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }
}
