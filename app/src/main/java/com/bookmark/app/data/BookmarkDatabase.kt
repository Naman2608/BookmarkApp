package com.bookmark.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bookmark.app.Book
import com.bookmark.app.Note
import com.bookmark.app.ReadingSession

@Database(
    entities = [Book::class, Note::class, ReadingSession::class],
    version = 1,
    exportSchema = false
)
abstract class BookmarkDatabase : RoomDatabase() {
    
    abstract fun bookDao(): BookDao
    abstract fun noteDao(): NoteDao
    abstract fun readingSessionDao(): ReadingSessionDao
    
    companion object {
        @Volatile
        private var INSTANCE: BookmarkDatabase? = null
        
        fun getDatabase(context: Context): BookmarkDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookmarkDatabase::class.java,
                    "bookmark_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
