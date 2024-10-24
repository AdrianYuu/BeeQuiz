package edu.bluejack23_2.beequiz.util

import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader

object RootDetector {

    fun isRoot(): Boolean {
        return rootValidation1() || rootValidation2() || rootValidation3();
    }

    private fun rootValidation2(): Boolean {
        val paths = arrayOf(
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su",
            "/su/bin/su"
        )
        for (path in paths) {
            if (File(path).exists()) return true
        }
        return false
    }

    private fun rootValidation1(): Boolean {
        val tags: String = android.os.Build.TAGS;
        return tags != null && tags.contains("test-keys");
    }

    private fun rootValidation3(): Boolean {
        var process: Process? = null
        return try {
            process = Runtime.getRuntime().exec(arrayOf("/system/xbin/which", "su"));
            val inBuf = BufferedReader(InputStreamReader(process.inputStream));
            (inBuf.readLine() != null);
        } catch (t: Throwable) {
            false
        } finally {
            process?.destroy()
        }
    }

}