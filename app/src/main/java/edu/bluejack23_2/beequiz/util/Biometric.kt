package edu.bluejack23_2.beequiz.util

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.*
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import edu.bluejack23_2.beequiz.activity.user.MainActivity
import edu.bluejack23_2.beequiz.helper.SessionHelper
import edu.bluejack23_2.beequiz.helper.ToastHelper
import edu.bluejack23_2.beequiz.repository.UserRepository

object Biometric {

    fun canBiometric(activity: Activity): Boolean {
        val biometricManager = BiometricManager.from(activity)
        return when (biometricManager.canAuthenticate(BIOMETRIC_WEAK)) {
            BiometricManager.BIOMETRIC_SUCCESS ->
                true
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                false
            else ->
                false
        }
    }

    private fun showPrompt(activity: Activity): BiometricPrompt.PromptInfo {
        return BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric login for BeeQuiz")
            .setSubtitle("Log in using your biometric credential")
            .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
            .build()
    }

    fun biometricAuth(activity: FragmentActivity) {
        val executor = ContextCompat.getMainExecutor(activity)
        val biometricPrompt = BiometricPrompt(activity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    ToastHelper.showToast(activity, "Authentication error", "Short")
                }
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    val userRepository = UserRepository()
                    val username = userRepository.getSharedPreferenceUser(activity)
                    if (username != "") {
                        if (username != null) {
                            userRepository.getUserByNIM(username) { user ->
                                if (user != null) {
//                                    if (user.name == )
                                    ToastHelper.showToast(
                                        activity,
                                        "Authentication success",
                                        "Short"
                                    )
                                    SessionHelper.setCurrentUser(user)
                                    Log.e("user", user.toString())
                                    val intent = Intent(activity, MainActivity::class.java)
                                    activity.startActivity(intent)
                                }
                            }
                        } else {
                            ToastHelper.showToast(activity, "Authentication failed", "Short")
                        }
                    } else {
                        ToastHelper.showToast(activity, "Authentication failed", "Short")
                    }
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    ToastHelper.showToast(activity, "Authentication failed", "Short")
                }
            })
        biometricPrompt.authenticate(showPrompt(activity))
    }

}