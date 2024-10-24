package edu.bluejack23_2.beequiz.fragment.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.bluejack23_2.beequiz.activity.user.MainActivity
import edu.bluejack23_2.beequiz.databinding.FragmentStartQuizBinding
import edu.bluejack23_2.beequiz.helper.FragmentHelper
import edu.bluejack23_2.beequiz.`interface`.IFragment
import edu.bluejack23_2.beequiz.view_model.StartQuizViewModel

class StartQuizFragment : Fragment(), IFragment {

    private var _binding: FragmentStartQuizBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: StartQuizViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this._binding = FragmentStartQuizBinding.inflate(inflater, container, false)
        this.viewModel = StartQuizViewModel()
        this.init()
        this.setEventListener()
        return binding.root
    }

    override fun init() {
        viewModel.reset()
        binding.quizTitle.text = viewModel.getCurrentQuiz().title
        binding.quizTotalQuestion.text =
            "${viewModel.getCurrentQuiz().questions!!.size} Question(s)"
    }

    override fun setEventListener() {
        binding.buttonStartQuizBack.setOnClickListener {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            requireActivity().startActivity(intent)
        }

        binding.buttonStartQuiz.setOnClickListener {
            FragmentHelper.replaceFragment(
                requireActivity().supportFragmentManager,
                OngoingQuizFragment()
            )
        }

        binding.buttonLeaderboard.setOnClickListener {
            FragmentHelper.replaceFragment(
                requireActivity().supportFragmentManager,
                LeaderboardFragment()
            )
        }
    }

}