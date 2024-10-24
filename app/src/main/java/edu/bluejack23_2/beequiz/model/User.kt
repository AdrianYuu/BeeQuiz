package edu.bluejack23_2.beequiz.model

data class User(
    val nim: String?,
    val name: String?,
    val password:String?
) {
    constructor() : this(null, null, null)
}
