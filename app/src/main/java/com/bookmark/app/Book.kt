package com.bookmark.app

data class Book(
    val id: Long,
    val title: String,
    val author: String = "",
    val currentPage: Int,
    val totalPages: Int,
    val currentChapter: String = "",
    val progressColor: String = "#E91E63", // Default purple
    val isCompleted: Boolean = false,
    val dateAdded: Long = System.currentTimeMillis()
) {
    fun getProgressPercentage(): Int {
        return if (totalPages > 0) {
            ((currentPage.toFloat() / totalPages.toFloat()) * 100).toInt()
        } else 0
    }
    
    fun getProgressText(): String {
        return "$currentPage of $totalPages"
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
            else -> android.R.color.holo_red_light
        }
    }
}