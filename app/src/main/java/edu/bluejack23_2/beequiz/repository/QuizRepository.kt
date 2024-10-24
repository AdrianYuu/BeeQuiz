package edu.bluejack23_2.beequiz.repository

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import edu.bluejack23_2.beequiz.database.Connection
import edu.bluejack23_2.beequiz.helper.QuizHelper
import edu.bluejack23_2.beequiz.model.Quiz
import edu.bluejack23_2.beequiz.model.Scoreboard
import edu.bluejack23_2.beequiz.model.User
import java.util.Collections

class QuizRepository {

    fun addQuiz(quiz: Quiz) {
        val database = Connection.getDatabase()
        val ref = database.getReference("/quiz")
        ref.child(quiz.code!!).setValue(quiz)
    }

    fun getQuizByCode(code: String, callback: (Quiz?) -> Unit) {
        val database = Connection.getDatabase()
        val ref = database.getReference("/quiz")

        ref.child(code).get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                val quiz = dataSnapshot.getValue(Quiz::class.java)
                callback(quiz)
            } else {
                callback(null)
            }
        }.addOnFailureListener {
            callback(null)
        }
    }

    fun addResult() {
        val database = Connection.getDatabase()
        val ref = database.getReference("/quiz")
        ref.child(QuizHelper.getCurrentQuiz().code!!).child("results")
            .setValue(QuizHelper.getCurrentQuiz().results)
    }

    fun getQuizzes(callback: (ArrayList<Quiz>?) -> Unit) {
        val database = Connection.getDatabase()
        val ref = database.getReference("/quiz")

        ref.get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                val quizzes = ArrayList<Quiz>()
                for (snapshot in dataSnapshot.children) {
                    val quiz = snapshot.getValue(Quiz::class.java)
                    quizzes.add(quiz!!)
                }
                quizzes.sortByDescending { it.results!!.size }
                callback(quizzes)
            } else {
                callback(null)
            }
        }.addOnFailureListener {
            callback(null)
        }
    }

    fun getSearchedQuizzesByTitle(title: String, callback: (ArrayList<Quiz>?) -> Unit) {
        val database = Connection.getDatabase()
        val ref = database.getReference("/quiz")

        ref.get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                val quizzes = ArrayList<Quiz>()
                for (childSnapshot in dataSnapshot.children) {
                    val quiz = childSnapshot.getValue(Quiz::class.java)
                    if (quiz!!.title!!.contains(title, ignoreCase = true)) quizzes.add(quiz!!)
                }
                callback(quizzes)
            } else {
                callback(null)
            }
        }.addOnFailureListener {
            callback(null)
        }
    }

    fun getQuizzesByNIM(nim: String, callback: (ArrayList<Quiz>?) -> Unit) {
        val database = Connection.getDatabase()
        val ref = database.getReference("/quiz")

        ref.get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                val quizzes = ArrayList<Quiz>()
                for (childSnapshot in dataSnapshot.children) {
                    val quiz = childSnapshot.getValue(Quiz::class.java)
                    if (quiz!!.creatorNim!! == nim) {
                        quizzes.add(quiz!!)
                    }
                }
                callback(quizzes)
            } else {
                callback(null)
            }
        }
    }

    fun getQuizzesHistoryByDate(
        nim: String,
        month: Int,
        year: Int,
        callback: (ArrayList<Quiz>?) -> Unit
    ) {
        val database = Connection.getDatabase()
        val ref = database.getReference("/quiz")

        ref.get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                val quizzes = ArrayList<Quiz>()
                for (childSnapshot in dataSnapshot.children) {
                    val quiz = childSnapshot.getValue(Quiz::class.java)
                    if (quiz!!.results!!.any { it.user!!.nim == nim && it.month == month && it.year == year }) {
                        quizzes.add(quiz)
                    }
                }
                callback(quizzes)
            } else {
                callback(null)
            }
        }.addOnFailureListener {
            callback(null)
        }
    }

    fun getQuizResult(quiz: Quiz, callback: (ArrayList<Scoreboard>?) -> Unit) {
        val database = Connection.getDatabase()
        val ref = database.getReference("/quiz")

        ref.child(quiz.code!!).child("results").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    var quizzes = ArrayList<Scoreboard>()

                    for (childSnapshot in dataSnapshot.children) {
                        val scoreboard = childSnapshot.getValue(Scoreboard::class.java)
                        scoreboard?.let { quizzes.add(it) }
                    }

                    quizzes.sortByDescending { it.score }

                    if (quizzes.size > 1) {
                        quizzes = ArrayList(quizzes.take(10))
                    }

                    callback(quizzes)
                } else {
                    callback(null)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                callback(null)
            }
        })
    }
}