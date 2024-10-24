package edu.bluejack23_2.beequiz.database

import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

object Connection {

    private val database = Firebase.database

    fun getDatabase(): FirebaseDatabase {
        return this.database
    }

}