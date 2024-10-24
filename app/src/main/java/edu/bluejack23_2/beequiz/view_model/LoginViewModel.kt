package edu.bluejack23_2.beequiz.view_model

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.fragment.app.FragmentActivity
import edu.bluejack23_2.beequiz.activity.user.MainActivity
import edu.bluejack23_2.beequiz.helper.ToastHelper
import edu.bluejack23_2.beequiz.repository.UserRepository
import edu.bluejack23_2.beequiz.util.Biometric
import edu.bluejack23_2.beequiz.util.RootDetector
import kotlin.system.exitProcess


class LoginViewModel {

    private val userRepository: UserRepository = UserRepository()

    fun validateBiometric(activity: Activity): Boolean {
        if (!Biometric.canBiometric(activity)) {
            ToastHelper.showToast(activity, "This device doesn't support biometric", "Long")
            return false
        }
        return true
    }

    fun validateRoot(activity: Activity) {
        if (RootDetector.isRoot()) {
            ToastHelper.showToast(activity, "This device is rooted", "short")
            exitProcess(0)
        }
    }

    fun login(activity: Activity, nim: String, password: String) {
        if (nim.isEmpty() || password.isEmpty()) {
            ToastHelper.showToast(activity, "NIM and password can't be empty!", "Long")
            return
        }

        userRepository.loginUsingFirebase(nim, password) { res ->
            val isExistsInFirebase = res ?: false

            if (!isExistsInFirebase && !userRepository.login(nim, password)) {
                ToastHelper.showToast(activity, "Invalid Username Or Incorrect Password!", "Long")
            } else {
                ToastHelper.showToast(activity, "Login successful!", "Short")
                userRepository.setSharedPreferenceUser(activity, nim)
                val intent = Intent(activity, MainActivity::class.java)
                activity.startActivity(intent)
            }
        }

    }

    fun loginBiometric(activity: FragmentActivity) {
        Biometric.biometricAuth(activity)
    }

}