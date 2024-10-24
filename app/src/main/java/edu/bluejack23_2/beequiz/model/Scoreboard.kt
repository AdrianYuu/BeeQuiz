package edu.bluejack23_2.beequiz.model

data class Scoreboard(
    var user: User?,
    var score: Int?,
    val month: Int?,
    val year: Int?
) {
    constructor() : this(null, null, null, null)
}