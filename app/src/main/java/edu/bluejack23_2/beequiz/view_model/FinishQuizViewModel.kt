package edu.bluejack23_2.beequiz.view_model

import edu.bluejack23_2.beequiz.helper.QuizHelper
import edu.bluejack23_2.beequiz.model.Quiz

class FinishQuizViewModel {

    fun getCurrentQuiz(): Quiz {
        return QuizHelper.getCurrentQuiz()
    }

    fun getScore(): Int {
        return QuizHelper.getScore()
    }

    fun getCorrectAnswerAmount(): Int {
        return QuizHelper.getCorrectAnswerAmount()
    }

}