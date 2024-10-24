package edu.bluejack23_2.beequiz.fragment.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import edu.bluejack23_2.beequiz.databinding.FragmentFinishQuizBinding
import edu.bluejack23_2.beequiz.helper.FragmentHelper
import edu.bluejack23_2.beequiz.`interface`.IFragment
import edu.bluejack23_2.beequiz.view_model.FinishQuizViewModel

class FinishQuizFragment : Fragment(), IFragment {

    private var _binding: FragmentFinishQuizBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FinishQuizViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this._binding = FragmentFinishQuizBinding.inflate(inflater, container, false)
        this.viewModel = FinishQuizViewModel()
        this.init()
        this.setEventListener()
        return binding.root
    }

    override fun init() {
        binding.textScore.text = viewModel.getScore().toString()
        binding.textCorrectAmount.text = viewModel.getCorrectAnswerAmount()
            .toString() + " out of " + viewModel.getCurrentQuiz().questions!!.size.toString()
    }

    override fun setEventListener() {
        binding.buttonFinishQuiz.setOnClickListener {
            FragmentHelper.replaceFragment(requireActivity().supportFragmentManager, HomeFragment())
        }
    }

}