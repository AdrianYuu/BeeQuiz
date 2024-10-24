package edu.bluejack23_2.beequiz.helper

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.journeyapps.barcodescanner.BarcodeCallback
import edu.bluejack23_2.beequiz.fragment.user.ScanQrFragment


object CameraHelper {

    fun checkCameraPermission(
        context: Context,
        fragment: ScanQrFragment,
        callback: BarcodeCallback
    ) {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fragment.binding.qrCodeView.decodeContinuous(callback)
        } else if (fragment.activity?.shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA) == true) {
            ToastHelper.showToast(context, "Camera permission is required", "Short")
        } else {
            fragment.requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }


}