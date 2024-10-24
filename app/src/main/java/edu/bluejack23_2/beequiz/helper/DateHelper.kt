package edu.bluejack23_2.beequiz.helper

import java.time.LocalDate

object DateHelper {

    fun getMonth(): Int {
        val currentDate = LocalDate.now()
        return currentDate.monthValue
    }

    fun getYear(): Int {
        val currentDate = LocalDate.now()
        return currentDate.year
    }

}