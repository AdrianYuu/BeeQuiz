package edu.bluejack23_2.beequiz.model

data class Question(
    var title: String?,
    var options: ArrayList<Option>?
) {
    constructor() : this(null, null)
}