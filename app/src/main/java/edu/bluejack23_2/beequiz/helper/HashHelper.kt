package edu.bluejack23_2.beequiz.helper

import org.mindrot.jbcrypt.BCrypt

object HashHelper {

    fun hashPassword(password: String): String{
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    fun verifyPassword(password: String, hashedPassword: String): Boolean{
        return BCrypt.checkpw(password, hashedPassword)
    }

}