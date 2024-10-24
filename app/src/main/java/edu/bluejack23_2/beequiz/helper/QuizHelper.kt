package edu.bluejack23_2.beequiz.helper

import android.util.Log
import edu.bluejack23_2.beequiz.model.Question
import edu.bluejack23_2.beequiz.model.Quiz
import java.util.Collections

object QuizHelper {

    private lateinit var currentQuiz: Quiz
    private var currentQuestionNumberIdx = 0
    private lateinit var currentAnswers: ArrayList<Int>

    fun reset() {
        this.currentQuestionNumberIdx = 0
        this.currentAnswers = ArrayList()
    }

    fun setCurrentQuiz(quiz: Quiz) {
        this.currentQuiz = quiz
        this.currentAnswers = ArrayList(Collections.nCopies(this.currentQuiz.questions!!.size, -1))
    }

    fun getCurrentQuiz(): Quiz {
        return this.currentQuiz
    }

    fun isLastQuestion(): Boolean {
        return this.currentQuiz.questions!!.size - 1 == this.currentQuestionNumberIdx
    }

    fun getCurrentQuestion(): Question {
        return this.currentQuiz.questions!![this.currentQuestionNumberIdx]
    }

    fun setCurrentQuestionNumberIdx(idx: Int) {
        if (idx < 0 || idx > this.currentQuiz.questions!!.size - 1) return
        this.currentQuestionNumberIdx = idx
    }

    fun getCurrentQuestionNumberIdx(): Int {
        return this.currentQuestionNumberIdx
    }

    fun updateAnswer(answer: Int) {
        try {
            this.currentAnswers[currentQuestionNumberIdx] = answer
        } catch (e: java.lang.Exception) {
            this.currentAnswers.add(answer)
        }
    }

    fun getCurrentQuestionAnswer(): Int {
        return try {
            this.currentAnswers[this.currentQuestionNumberIdx]
        } catch (e: java.lang.Exception) {
            -1
        }
    }

    fun getCorrectAnswerAmount(): Int {
        var amount = 0

        for (i in currentAnswers.indices) {
            val question = this.currentQuiz.questions!![i]
            val userAnswerIndex = this.currentAnswers[i]
            if(userAnswerIndex == -1) continue
            val selectedOption = question.options?.get(userAnswerIndex)
            if (selectedOption?.isCorrect == true) amount++
        }

        return amount
    }

    fun getScore(): Int {
        return (this.getCorrectAnswerAmount() * 100 / this.currentQuiz.questions!!.size)
    }

}