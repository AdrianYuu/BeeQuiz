package edu.bluejack23_2.beequiz.fragment.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import edu.bluejack23_2.beequiz.adapter.DefaultQuizAdapter
import edu.bluejack23_2.beequiz.databinding.FragmentHomeBinding
import edu.bluejack23_2.beequiz.helper.FragmentHelper
import edu.bluejack23_2.beequiz.`interface`.IFragment
import edu.bluejack23_2.beequiz.model.Quiz
import edu.bluejack23_2.beequiz.view_model.HomeViewModel

class HomeFragment : Fragment(), IFragment {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel
    private lateinit var quizAdapter: DefaultQuizAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this._binding = FragmentHomeBinding.inflate(inflater, container, false)
        this.viewModel = HomeViewModel()
        this.init()
        this.setEventListener()
        return binding.root
    }

    override fun init() {
        binding.textUsername.text = viewModel.getCurrentUser().name

        viewModel.getQuizzes { quizzes ->
            if (quizzes != null) {
                setupAdapter(quizzes)
            }
        }
    }

    private fun setupAdapter(quizzes: ArrayList<Quiz>) {
        this.quizAdapter = DefaultQuizAdapter(quizzes,
            onClick = { quiz ->
                viewModel.setCurrentQuiz(quiz)
                FragmentHelper.replaceFragment(
                    requireActivity().supportFragmentManager,
                    StartQuizFragment()
                )
            }
        )
        binding.recyclerViewRecommendedQuiz.adapter = quizAdapter
        if(isAdded) binding.recyclerViewRecommendedQuiz.layoutManager = LinearLayoutManager(requireContext())
        this.checkEmpty()
    }

    override fun setEventListener() {
        binding.joinQuizButton.setOnClickListener {
            viewModel.joinQuiz(binding.inputQuizCode.text.toString(), requireActivity())
        }
    }

    private fun checkEmpty() {
        binding.textRecyclerViewRecommendedQuizEmpty.visibility =
            if (this.quizAdapter.itemCount == 0) View.VISIBLE else View.GONE
    }

}