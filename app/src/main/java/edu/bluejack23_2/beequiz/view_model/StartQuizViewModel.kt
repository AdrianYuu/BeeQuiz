package edu.bluejack23_2.beequiz.view_model

import edu.bluejack23_2.beequiz.helper.QuizHelper
import edu.bluejack23_2.beequiz.model.Quiz

class StartQuizViewModel {

    fun reset() {
        QuizHelper.reset()
    }

    fun getCurrentQuiz(): Quiz {
        return QuizHelper.getCurrentQuiz()
    }
}