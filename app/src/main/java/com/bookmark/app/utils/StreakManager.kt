package com.bookmark.app.utils

import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

data class StreakInfo(
    val daily: Int,
    val weekly: Int,
    val monthly: Int
)

object StreakManager {
    
    private fun normalizeDate(timestamp: Long): Long {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = timestamp
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis
    }
    
    /**
     * Calculate reading streak based on unique reading dates
     * @param dates List of date strings in format "YYYY-MM-DD"
     */
    fun calculateReadingStreak(dates: List<String>): StreakInfo {
        if (dates.isEmpty()) {
            return StreakInfo(0, 0, 0)
        }
        
        val sortedDates = dates.distinct().sorted().reversed() // Most recent first
        
        val dailyStreak = calculateDailyStreak(sortedDates)
        val weeklyStreak = calculateWeeklyStreak(sortedDates)
        val monthlyStreak = calculateMonthlyStreak(sortedDates)
        
        return StreakInfo(dailyStreak, weeklyStreak, monthlyStreak)
    }
    
    private fun calculateDailyStreak(sortedDates: List<String>): Int {
        if (sortedDates.isEmpty()) return 0
        
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today = Calendar.getInstance()
        val todayStr = dateFormat.format(today.time)
        
        val yesterday = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -1)
        }
        val yesterdayStr = dateFormat.format(yesterday.time)
        
        // Check if user read today or yesterday
        if (sortedDates[0] != todayStr && sortedDates[0] != yesterdayStr) {
            return 0
        }
        
        var streak = 0
        var expectedDate = Calendar.getInstance()
        
        for (dateStr in sortedDates) {
            val expectedDateStr = dateFormat.format(expectedDate.time)
            
            if (dateStr == expectedDateStr) {
                streak++
                expectedDate.add(Calendar.DAY_OF_YEAR, -1)
            } else {
                break
            }
        }
        
        return streak
    }
    
    private fun calculateWeeklyStreak(sortedDates: List<String>): Int {
        if (sortedDates.isEmpty()) return 0
        
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        
        var currentWeek = getWeekOfYear(calendar)
        var currentYear = calendar.get(Calendar.YEAR)
        var streak = 0
        var foundCurrentWeek = false
        
        for (dateStr in sortedDates) {
            try {
                val date = dateFormat.parse(dateStr) ?: continue
                calendar.time = date
                
                val dateWeek = getWeekOfYear(calendar)
                val dateYear = calendar.get(Calendar.YEAR)
                
                if (dateYear == currentYear && dateWeek == currentWeek) {
                    if (!foundCurrentWeek) {
                        streak++
                        foundCurrentWeek = true
                    }
                } else {
                    // Check if it's the previous week
                    currentWeek--
                    if (currentWeek < 1) {
                        currentWeek = 52
                        currentYear--
                    }
                    
                    if (dateYear == currentYear && dateWeek == currentWeek) {
                        streak++
                        foundCurrentWeek = true
                    } else {
                        break
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        
        return streak
    }
    
    private fun calculateMonthlyStreak(sortedDates: List<String>): Int {
        if (sortedDates.isEmpty()) return 0
        
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val calendar = Calendar.getInstance()
        
        var currentMonth = calendar.get(Calendar.MONTH)
        var currentYear = calendar.get(Calendar.YEAR)
        var streak = 0
        var foundCurrentMonth = false
        
        for (dateStr in sortedDates) {
            try {
                val date = dateFormat.parse(dateStr) ?: continue
                calendar.time = date
                
                val dateMonth = calendar.get(Calendar.MONTH)
                val dateYear = calendar.get(Calendar.YEAR)
                
                if (dateYear == currentYear && dateMonth == currentMonth) {
                    if (!foundCurrentMonth) {
                        streak++
                        foundCurrentMonth = true
                    }
                } else {
                    // Check if it's the previous month
                    currentMonth--
                    if (currentMonth < 0) {
                        currentMonth = 11
                        currentYear--
                    }
                    
                    if (dateYear == currentYear && dateMonth == currentMonth) {
                        streak++
                        foundCurrentMonth = true
                    } else {
                        break
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        
        return streak
    }
    
    private fun getWeekOfYear(calendar: Calendar): Int {
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.minimalDaysInFirstWeek = 4
        return calendar.get(Calendar.WEEK_OF_YEAR)
    }
    
    /**
     * Get streak text for display (e.g., "D5", "W3", "M2")
     */
    fun getStreakText(type: String, count: Int): String {
        return when (type.uppercase()) {
            "DAILY", "D" -> "D$count"
            "WEEKLY", "W" -> "W$count"
            "MONTHLY", "M" -> "M$count"
            else -> "$count"
        }
    }
}
