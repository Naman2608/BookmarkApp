package com.bookmark.app.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bookmark.app.Note

@Dao
interface NoteDao {
    
    @Query("SELECT * FROM notes WHERE bookId = :bookId ORDER BY dateModified DESC")
    fun getNotesForBook(bookId: Long): LiveData<List<Note>>
    
    @Query("SELECT * FROM notes WHERE userId = :userId ORDER BY dateModified DESC")
    fun getAllNotes(userId: String): LiveData<List<Note>>
    
    @Query("SELECT * FROM notes WHERE id = :noteId")
    fun getNoteById(noteId: Long): LiveData<Note>
    
    @Query("SELECT COUNT(*) FROM notes WHERE bookId = :bookId")
    fun getNoteCountForBook(bookId: Long): LiveData<Int>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note): Long
    
    @Update
    suspend fun updateNote(note: Note)
    
    @Delete
    suspend fun deleteNote(note: Note)
    
    @Query("DELETE FROM notes WHERE id = :noteId")
    suspend fun deleteNoteById(noteId: Long)
    
    @Query("DELETE FROM notes WHERE bookId = :bookId")
    suspend fun deleteNotesForBook(bookId: Long)
}
