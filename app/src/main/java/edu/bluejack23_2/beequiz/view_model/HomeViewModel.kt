package edu.bluejack23_2.beequiz.view_model

import androidx.fragment.app.FragmentActivity
import edu.bluejack23_2.beequiz.fragment.user.StartQuizFragment
import edu.bluejack23_2.beequiz.helper.FragmentHelper
import edu.bluejack23_2.beequiz.helper.QuizHelper
import edu.bluejack23_2.beequiz.helper.SessionHelper
import edu.bluejack23_2.beequiz.helper.ToastHelper
import edu.bluejack23_2.beequiz.model.Quiz
import edu.bluejack23_2.beequiz.model.User
import edu.bluejack23_2.beequiz.repository.QuizRepository

class HomeViewModel() {

    private val quizRepository = QuizRepository()

    fun getCurrentUser(): User {
        return SessionHelper.getCurrentUser();
    }

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

    fun joinQuiz(text: String, activity: FragmentActivity) {
        if (text.isEmpty()) {
            ToastHelper.showToast(activity, "Quiz code must not be empty", "Short")
            return
        }

        if (text.length != 6) {
            ToastHelper.showToast(activity, "Quiz code length must be 6", "Short")
            return
        }

        if (text.toIntOrNull() == null) {
            ToastHelper.showToast(activity, "Quiz code must be numeric", "Short")
            return
        }

        quizRepository.getQuizByCode(text) { quiz ->
            if (quiz != null) {
                QuizHelper.setCurrentQuiz(quiz)
                FragmentHelper.replaceFragment(
                    activity.supportFragmentManager,
                    StartQuizFragment()
                )
            } else {
                ToastHelper.showToast(activity, "Quiz with this code not exists", "Short")
            }
        }
    }

}