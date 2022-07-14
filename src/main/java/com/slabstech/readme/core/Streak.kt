package com.slabstech.readme.core

import java.time.LocalDate

data class Streak(val currentStreakDays: Int, val longestStreakDays: Int, val currentStreakStartDate: LocalDate,
                  val currentStreakTodayDate: LocalDate, val longestStreakStartDate: LocalDate,
                  val longestStreakEndDate: LocalDate, val currentStreakTotalCommits: Int, val longStreaksTotalCommits: Int)
