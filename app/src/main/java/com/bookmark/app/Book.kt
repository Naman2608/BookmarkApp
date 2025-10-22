package com.bookmark.app

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val author: String = "",
    val subtitle: String = "",
    val isbn: String = "",
    val coverUrl: String = "",
    val currentPage: Int = 0,
    val totalPages: Int = 0,
    val currentChapter: String = "",
    val progressColor: String = "#E74C3C", // Default red from design
    val isCompleted: Boolean = false,
    val dateAdded: Long = System.currentTimeMillis(),
    val lastReadDate: Long = System.currentTimeMillis(),
    val userId: String = ""
) {
    fun getProgressPercentage(): Int {
        return if (totalPages > 0) {
            ((currentPage.toFloat() / totalPages.toFloat()) * 100).toInt()
        } else 0
    }
    
    fun getProgressText(): String {
        return "$currentPage/$totalPages Pages"
    }
    
    fun getChapterText(): String {
        return if (currentChapter.isNotEmpty()) {
            " • $currentChapter"
        } else ""
    }
    
    fun getProgressColorRes(): Int {
        return when (progressColor) {
            "#E91E63" -> android.R.color.holo_red_light
            "#4CAF50" -> android.R.color.holo_green_light
            "#FF9800" -> android.R.color.holo_orange_light
            "#2196F3" -> android.R.color.holo_blue_light
            "#F44336" -> android.R.color.holo_red_dark
            "#E74C3C" -> R.color.streak_red
            "#000000" -> android.R.color.black
            "#E0E0E0" -> android.R.color.darker_gray
            else -> R.color.streak_red
        }
    }
}
