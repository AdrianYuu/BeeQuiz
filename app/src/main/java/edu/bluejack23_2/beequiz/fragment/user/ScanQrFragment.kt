package edu.bluejack23_2.beequiz.fragment.user

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.google.zxing.ResultPoint
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import edu.bluejack23_2.beequiz.activity.user.MainActivity
import edu.bluejack23_2.beequiz.databinding.FragmentScanQrBinding
import edu.bluejack23_2.beequiz.helper.CameraHelper
import edu.bluejack23_2.beequiz.helper.FragmentHelper
import edu.bluejack23_2.beequiz.helper.QuizHelper
import edu.bluejack23_2.beequiz.helper.ToastHelper
import edu.bluejack23_2.beequiz.`interface`.IFragment
import edu.bluejack23_2.beequiz.view_model.ScanQrViewModel

class ScanQrFragment : Fragment(), IFragment {

    private var _binding: FragmentScanQrBinding? = null
    val binding get() = _binding!!
    private lateinit var viewModel: ScanQrViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this._binding = FragmentScanQrBinding.inflate(inflater, container, false)
        this.viewModel = ScanQrViewModel()
        this.init()
        this.setEventListener()
        if (container != null) CameraHelper.checkCameraPermission(requireActivity(), this, callback)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.qrCodeView.resume()
    }

    override fun onPause() {
        super.onPause()
        binding.qrCodeView.pause()
    }

    private val callback = object : BarcodeCallback {
        override fun barcodeResult(result: BarcodeResult) {
            result.text?.let {
                viewModel.getQuizByCode(it) { quiz ->
                    if (quiz != null) {
                        QuizHelper.setCurrentQuiz(quiz)
                        FragmentHelper.replaceFragment(
                            requireActivity().supportFragmentManager,
                            StartQuizFragment()
                        )
                    } else {
                        ToastHelper.showToast(requireActivity(), "Quiz not exists", "Short")
                    }
                }
            }
        }

        override fun possibleResultPoints(resultPoints: List<ResultPoint>) {
        }
    }

    val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                binding.qrCodeView.decodeContinuous(callback)
            } else {
                this.activity?.let {
                    ToastHelper.showToast(
                        it,
                        "You must enable camera to use this feature",
                        "Long"
                    )
                }
            }
        }

    override fun init() {
    }

    override fun setEventListener() {
        binding.buttonBackQr.setOnClickListener {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            requireActivity().startActivity(intent)
        }
    }

}