package com.bookmark.app

data class ReadingStreak(
    val dailyStreak: Int = 0,      // D5 = 5 days
    val weeklyStreak: Int = 0,     // W3 = 3 weeks
    val monthlyStreak: Int = 0,    // M2 = 2 months
    val dailyProgress: Float = 0f,  // 0.0 to 1.0
    val weeklyProgress: Float = 0f,
    val monthlyProgress: Float = 0f,
    val color: String = "#E74C3C"   // Red, Orange, Blue, Gray
) {
    fun getDailyLabel(): String = "D$dailyStreak"
    fun getWeeklyLabel(): String = "W$weeklyStreak"
    fun getMonthlyLabel(): String = "M$monthlyStreak"
    
    fun getDailyDescription(): String {
        return if (dailyStreak > 0) "$dailyStreak days of continuous reading" 
        else "Start reading today"
    }
    
    fun getWeeklyDescription(): String {
        return if (weeklyStreak > 0) "${getWeeklyOrdinal()} week of continuous reading"
        else "No weekly streak yet"
    }
    
    fun getMonthlyDescription(): String {
        return if (monthlyStreak > 0) "$monthlyStreak months of continuous reading"
        else "No monthly streak yet"
    }
    
    private fun getWeeklyOrdinal(): String {
        return when (weeklyStreak) {
            1 -> "First"
            2 -> "Second"
            3 -> "Third"
            4 -> "Fourth"
            else -> "${weeklyStreak}th"
        }
    }
}

data class WritingStreak(
    val dailyStreak: Int = 0,
    val weeklyStreak: Int = 0,
    val monthlyStreak: Int = 0,
    val dailyProgress: Float = 0f,
    val weeklyProgress: Float = 0f,
    val monthlyProgress: Float = 0f,
    val color: String = "#E74C3C"
) {
    fun getDailyLabel(): String = "D$dailyStreak"
    fun getWeeklyLabel(): String = "W$weeklyStreak"
    fun getMonthlyLabel(): String = "M$monthlyStreak"
    
    fun getDailyDescription(): String {
        return "First Day of continuous writing about my reading"
    }
}
