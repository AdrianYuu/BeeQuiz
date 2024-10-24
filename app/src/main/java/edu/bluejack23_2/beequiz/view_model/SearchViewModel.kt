package edu.bluejack23_2.beequiz.view_model

import android.app.Activity
import edu.bluejack23_2.beequiz.helper.QuizHelper
import edu.bluejack23_2.beequiz.helper.ToastHelper
import edu.bluejack23_2.beequiz.model.Quiz
import edu.bluejack23_2.beequiz.repository.QuizRepository

class SearchViewModel {

    private val quizRepository = QuizRepository()

    fun setCurrentQuiz(quiz: Quiz) {
        QuizHelper.setCurrentQuiz(quiz)
    }

    fun getQuizzes(callback: (ArrayList<Quiz>?) -> Unit) {
        quizRepository.getQuizzes { quizzes ->
            if (quizzes != null) {
                callback(quizzes)
            } else {
                callback(null)
            }
        }
    }

    fun getSearchedQuizByName(
        title: String,
        activity: Activity,
        callback: (ArrayList<Quiz>?) -> Unit
    ) {
        if (title.isEmpty()) {
            ToastHelper.showToast(activity, "Quiz title must not be empty", "Short")
            return
        }

        quizRepository.getSearchedQuizzesByTitle(title) { quizzes ->
            if (quizzes != null) {
                callback(quizzes)
            } else {
                callback(ArrayList())
            }
        }
    }

}