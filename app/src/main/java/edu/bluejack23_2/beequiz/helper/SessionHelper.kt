package edu.bluejack23_2.beequiz.helper

import edu.bluejack23_2.beequiz.model.User

object SessionHelper {

    private lateinit var currentUser: User

    fun setCurrentUser(user: User) {
        this.currentUser = user;
    }

    fun getCurrentUser(): User {
        return this.currentUser;
    }

}