package edu.bluejack23_2.beequiz.view_model

import edu.bluejack23_2.beequiz.helper.DateHelper
import edu.bluejack23_2.beequiz.helper.QuizHelper
import edu.bluejack23_2.beequiz.helper.SessionHelper
import edu.bluejack23_2.beequiz.model.Question
import edu.bluejack23_2.beequiz.model.Scoreboard
import edu.bluejack23_2.beequiz.repository.QuizRepository
import java.time.LocalDate

class OngoingQuizViewModel {

    private val quizRepository: QuizRepository = QuizRepository()

    fun getCurrentQuestion(): Question {
        return QuizHelper.getCurrentQuestion()
    }

    fun getCurrentQuestionNumberIdx(): Int {
        return QuizHelper.getCurrentQuestionNumberIdx()
    }

    fun setCurrentQuestionNumberIdx(idx: Int) {
        QuizHelper.setCurrentQuestionNumberIdx(idx)
    }

    fun isLastQuestion(): Boolean {
        return QuizHelper.isLastQuestion()
    }

    fun updateAnswer(answer: Int) {
        QuizHelper.updateAnswer(answer)
    }

    fun getCurrentQuestionAnswer(): Int {
        return QuizHelper.getCurrentQuestionAnswer()
    }

    fun addResult() {
        var result =
            Scoreboard(SessionHelper.getCurrentUser(), QuizHelper.getScore(), DateHelper.getMonth(), DateHelper.getYear())
        QuizHelper.getCurrentQuiz().results!!.add(result)
        quizRepository.addResult()
    }

}