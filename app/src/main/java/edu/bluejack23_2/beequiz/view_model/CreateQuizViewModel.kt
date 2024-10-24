package edu.bluejack23_2.beequiz.view_model

import android.app.Activity
import edu.bluejack23_2.beequiz.helper.RandomHelper
import edu.bluejack23_2.beequiz.helper.SessionHelper
import edu.bluejack23_2.beequiz.helper.ToastHelper
import edu.bluejack23_2.beequiz.model.Question
import edu.bluejack23_2.beequiz.model.Quiz
import edu.bluejack23_2.beequiz.model.User
import edu.bluejack23_2.beequiz.repository.QuizRepository

class CreateQuizViewModel {

    private val quizRepository = QuizRepository()
    private val questionList = ArrayList<Question>()

    private fun validateQuestion(question: Question, activity: Activity): Boolean {
        if (question.title!!.isEmpty()) {
            ToastHelper.showToast(activity, "Question title can't be empty!", "Long")
            return false
        }

        if (question.options!!.any { it.title!!.isEmpty() }) {
            ToastHelper.showToast(activity, "Options title can't be empty!", "Long")
            return false
        }

        if (question.options!!.none { it.isCorrect!! }) {
            ToastHelper.showToast(activity, "There must be a correct option", "Long")
            return false
        }

        return true
    }

    fun saveQuestion(question: Question, activity: Activity): Boolean {
        if (!validateQuestion(question, activity)) return false
        this.questionList.add(question)

        ToastHelper.showToast(activity, "Successfully added question!", "Short")
        return true
    }

    fun getQuestions(): ArrayList<Question> {
        return this.questionList
    }

    fun deleteQuestion(position: Int, activity: Activity) {
        this.questionList.removeAt(position)
        ToastHelper.showToast(activity, "Successfully delete question!", "Short")
    }

    fun updateQuestion(position: Int, question: Question, activity: Activity) {
        if (!validateQuestion(question, activity)) return
        this.questionList[position] = question

        ToastHelper.showToast(activity, "Successfully update question!", "Short")
    }

    private fun generateQuizCode(): String {
        lateinit var str: String
        do {
            str = String.format(
                "%d%d%d%d%d%d", RandomHelper.generateNumber(0, 9),
                RandomHelper.generateNumber(0, 9), RandomHelper.generateNumber(0, 9),
                RandomHelper.generateNumber(0, 9), RandomHelper.generateNumber(0, 9),
                RandomHelper.generateNumber(0, 9)
            )
        } while (!getQuiz(str))

        return str
    }

    private fun generateQuizColor(): String {
        return RandomHelper.generateColor()
    }

    private fun getCurrentUser(): User {
        return SessionHelper.getCurrentUser();
    }

    private fun getQuiz(str: String): Boolean {
        var quiz: Quiz? = null
        quizRepository.getQuizByCode(str) { q ->
            if (q != null) quiz = q
        }
        return quiz == null
    }

    private fun validateQuiz(title: String, activity: Activity): Boolean {
        if (title.isEmpty()) {
            ToastHelper.showToast(activity, "Quiz title can't be empty", "Short")
            return false
        }

        if (this.questionList.isEmpty()) {
            ToastHelper.showToast(activity, "There must be at least 1 questions!", "Short")
            return false
        }

        return true
    }

    fun createQuiz(title: String, activity: Activity): Boolean {
        val code = this.generateQuizCode()
        val color = this.generateQuizColor()

        val quiz = Quiz(title, this.getCurrentUser().nim, code, color, this.questionList, null)

        if (!validateQuiz(title, activity)) return false

        quizRepository.addQuiz(quiz)

        ToastHelper.showToast(activity, "Successfully create quiz!", "Short")
        return true
    }

}