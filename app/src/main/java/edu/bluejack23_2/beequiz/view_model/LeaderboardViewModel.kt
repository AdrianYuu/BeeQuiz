package edu.bluejack23_2.beequiz.view_model

import edu.bluejack23_2.beequiz.helper.QuizHelper
import edu.bluejack23_2.beequiz.model.Scoreboard
import edu.bluejack23_2.beequiz.repository.QuizRepository

class LeaderboardViewModel {

    private val quizRepository = QuizRepository()

    fun getLeaderboard(callback: (ArrayList<Scoreboard>?) -> Unit) {
        quizRepository.getQuizResult(QuizHelper.getCurrentQuiz()) { scores ->
            if (scores != null) {
                callback(scores)
            } else {
                callback(ArrayList())
            }
        }
    }


}