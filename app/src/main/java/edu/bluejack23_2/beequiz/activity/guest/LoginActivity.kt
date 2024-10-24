package edu.bluejack23_2.beequiz.activity.guest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import edu.bluejack23_2.beequiz.databinding.ActivityLoginBinding
import edu.bluejack23_2.beequiz.`interface`.IActivity
import edu.bluejack23_2.beequiz.view_model.LoginViewModel

class LoginActivity : AppCompatActivity(), IActivity {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityLoginBinding.inflate(layoutInflater)
        this.viewModel = LoginViewModel()
        this.init()
        this.setEventListener()
        this.setContentView(binding.root)
    }

    override fun init() {
        if (!viewModel.validateBiometric(this)) {
            binding.buttonBiometric.visibility = View.GONE
            binding.textOr.visibility = View.GONE
        }
        viewModel.validateRoot(this)
    }

    override fun setEventListener() {
        binding.buttonLogin.setOnClickListener {
            val nim = binding.inputNim.text.toString()
            val password = binding.inputPassword.text.toString()
            viewModel.login(this, nim, password)
        }

        binding.buttonBiometric.setOnClickListener {
            viewModel.loginBiometric(this)
        }
    }

}