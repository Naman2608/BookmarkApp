package com.bookmark.app

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reading_sessions")
data class ReadingSession(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val bookId: Long,
    val date: Long = System.currentTimeMillis(),
    val pagesRead: Int = 0,
    val startPage: Int = 0,
    val endPage: Int = 0,
    val durationMinutes: Int = 0,
    val userId: String = ""
)
