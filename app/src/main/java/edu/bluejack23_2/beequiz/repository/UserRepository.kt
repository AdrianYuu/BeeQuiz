package edu.bluejack23_2.beequiz.repository

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.StrictMode
import android.util.Log
import edu.bluejack23_2.beequiz.BuildConfig
import edu.bluejack23_2.beequiz.database.Connection
import edu.bluejack23_2.beequiz.helper.HashHelper
import edu.bluejack23_2.beequiz.helper.SessionHelper
import edu.bluejack23_2.beequiz.model.User
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class UserRepository {

    fun login(nim: String, password: String): Boolean {

        if (Build.VERSION.SDK_INT > 8) {
            StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
        }

        val url = URL(BuildConfig.SERVER_URL + BuildConfig.LOGIN_ENDPOINT);
        val reqParam = "username=${URLEncoder.encode(nim, "UTF-8")}&password=${
            URLEncoder.encode(
                password,
                "UTF-8"
            )
        }"

        (url.openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            doOutput = true

            OutputStreamWriter(outputStream).use { it.write(reqParam) }

            if (responseCode != HttpURLConnection.HTTP_OK) return false

            BufferedReader(InputStreamReader(inputStream)).use {
                val response = it.readText()
                val jsonObject = JSONObject(response).getJSONObject("User")

                val currentUser =
                    User(
                        jsonObject.getString("UserName"),
                        jsonObject.getString("Name"),
                        HashHelper.hashPassword(password)
                    )

                SessionHelper.setCurrentUser(currentUser)

                getUserByNIM(jsonObject.getString("UserName")) { user ->
                    if (user == null) addUser(currentUser)
                }
            }
        }
        return true;
    }

    fun loginUsingFirebase(nim: String, password: String, callback: (Boolean?) -> Unit) {
        val database = Connection.getDatabase()
        val ref = database.getReference("/user")

        ref.child(nim).get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                val user = dataSnapshot.getValue(User::class.java)

                if (user != null && HashHelper.verifyPassword(password, user.password!!)) {
                    SessionHelper.setCurrentUser(user)
                    callback(true)
                } else {
                    callback(false)
                }
            } else {
                callback(false)
            }
        }.addOnFailureListener {
            callback(false)
        }
    }

    fun getUserByNIM(nim: String, callback: (User?) -> Unit) {
        val database = Connection.getDatabase()
        val ref = database.getReference("/user")

        ref.child(nim).get().addOnSuccessListener { dataSnapshot ->
            if (dataSnapshot.exists()) {
                val user = dataSnapshot.getValue(User::class.java)
                callback(user)
            } else {
                callback(null)
            }
        }.addOnFailureListener {
            callback(null)
        }
    }

    fun getSharedPreferenceUser(activity: Activity): String? {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return ""
        return sharedPref.getString("NIM", "")
    }

    fun setSharedPreferenceUser(activity: Activity, nim: String) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString("NIM", nim)
            apply()
        }
    }

    fun addUser(user: User) {
        val database = Connection.getDatabase()
        val ref = database.getReference("/user")
        ref.child(user.nim!!).setValue(user)
    }

}