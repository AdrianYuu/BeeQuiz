package edu.bluejack23_2.beequiz.model

data class Quiz(
    var title: String?,
    var creatorNim: String?,
    var code: String?,
    val color: String?,
    var questions: ArrayList<Question>?,
    var results: ArrayList<Scoreboard>? = ArrayList()
) {
    constructor() : this(null, null, null, null, null, ArrayList())
}