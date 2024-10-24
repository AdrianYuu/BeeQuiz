package edu.bluejack23_2.beequiz.view_model

import edu.bluejack23_2.beequiz.model.Quiz
import edu.bluejack23_2.beequiz.repository.QuizRepository

class ScanQrViewModel {

    private val quizRepository = QuizRepository()

    fun getQuizByCode(code: String, callback: (Quiz?) -> Unit) {
        quizRepository.getQuizByCode(code) { quiz ->
            if (quiz != null) {
                callback(quiz)
            } else {
                callback(null)
            }
        }
    }

}