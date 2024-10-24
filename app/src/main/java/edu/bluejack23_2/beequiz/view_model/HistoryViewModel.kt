package edu.bluejack23_2.beequiz.view_model

import android.app.Activity
import edu.bluejack23_2.beequiz.helper.SessionHelper
import edu.bluejack23_2.beequiz.helper.ToastHelper
import edu.bluejack23_2.beequiz.model.Quiz
import edu.bluejack23_2.beequiz.model.User
import edu.bluejack23_2.beequiz.repository.QuizRepository

class HistoryViewModel {

    private val quizRepository = QuizRepository()

    fun getCurrentUser(): User {
        return SessionHelper.getCurrentUser();
    }

    fun fetchQuiz(
        nim: String,
        month: String,
        year: String,
        activity: Activity,
        callback: (ArrayList<Quiz>?) -> Unit
    ) {
        if (month.isEmpty()) {
            ToastHelper.showToast(activity, "Month can't be empty!", "Long")
            return
        }

        if (year.isEmpty()) {
            ToastHelper.showToast(activity, "Year can't be empty!", "Long")
            return
        }

        if (month.length > 2) {
            ToastHelper.showToast(activity, "Month must be between 1 or 2 number!", "Long")
            return
        }

        if (month.toInt() < 1 || month.toInt() > 12) {
            ToastHelper.showToast(activity, "Month must be between 1 and 12!", "Long")
            return
        }

        if (year.length != 4) {
            ToastHelper.showToast(activity, "Year must be 4 number!", "Long")
            return
        }

        getQuizzesHistoryByDate(nim, month.toInt(), year.toInt()) { quizzes ->
            if (quizzes != null) {
                callback(quizzes)
            } else {
                callback(ArrayList())
            }
        }
    }

    private fun getQuizzesHistoryByDate(
        nim: String,
        month: Int,
        year: Int,
        callback: (ArrayList<Quiz>?) -> Unit
    ) {
        quizRepository.getQuizzesHistoryByDate(nim, month, year) { quizzes ->
            if (quizzes != null) {
                callback(quizzes)
            } else {
                callback(null)
            }
        }
    }

}