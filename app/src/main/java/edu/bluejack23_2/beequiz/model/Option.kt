package edu.bluejack23_2.beequiz.model

data class Option(
    var title: String?,
    var isCorrect: Boolean?
) {
    constructor() : this(null, null)
}
