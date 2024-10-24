package edu.bluejack23_2.beequiz.helper

import android.content.Context
import android.widget.Toast

object ToastHelper {

    fun showToast(context: Context, text: String, length: String) {
        Toast.makeText(
            context,
            text,
            if (length == "Long") Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        ).show()
    }

}