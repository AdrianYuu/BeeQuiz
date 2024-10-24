package edu.bluejack23_2.beequiz.view_model

import edu.bluejack23_2.beequiz.helper.SessionHelper
import edu.bluejack23_2.beequiz.model.Quiz
import edu.bluejack23_2.beequiz.model.User
import edu.bluejack23_2.beequiz.repository.QuizRepository

class MyQuizViewModel {

    private val quizRepository = QuizRepository()


    fun getCurrentUser(): User {
        return SessionHelper.getCurrentUser();
    }

    fun getQuizzesByNIM(nim: String, callback: (ArrayList<Quiz>?) -> Unit) {
        quizRepository.getQuizzesByNIM(nim) { quizzes ->
            if (quizzes != null) {
                callback(quizzes)
            } else {
                callback(ArrayList())
            }
        }
    }

}