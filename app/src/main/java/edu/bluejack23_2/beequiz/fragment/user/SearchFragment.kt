package edu.bluejack23_2.beequiz.fragment.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import edu.bluejack23_2.beequiz.adapter.DefaultQuizAdapter
import edu.bluejack23_2.beequiz.databinding.FragmentSearchBinding
import edu.bluejack23_2.beequiz.helper.FragmentHelper
import edu.bluejack23_2.beequiz.`interface`.IFragment
import edu.bluejack23_2.beequiz.model.Quiz
import edu.bluejack23_2.beequiz.view_model.SearchViewModel

class SearchFragment : Fragment(), IFragment {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SearchViewModel
    private lateinit var quizAdapter: DefaultQuizAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this._binding = FragmentSearchBinding.inflate(inflater, container, false)
        this.viewModel = SearchViewModel()
        this.init()
        this.setEventListener()
        return binding.root
    }

    override fun init() {
        viewModel.getQuizzes { quizzes ->
            if (quizzes != null) {
                setupAdapter(quizzes)
            } else {
                setupAdapter(ArrayList())
            }
        }
    }

    override fun setEventListener() {
        binding.buttonSearchQuiz.setOnClickListener {
            viewModel.getSearchedQuizByName(
                binding.inputQuizTitle.text.toString(),
                requireActivity()
            ) { quizzes ->
                if (quizzes != null) {
                    this.quizAdapter.updateData(quizzes)
                    this.checkEmpty()
                }
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
        binding.recyclerViewSearchedQuiz.adapter = quizAdapter
        if(isAdded) binding.recyclerViewSearchedQuiz.layoutManager = LinearLayoutManager(requireContext())
        this.checkEmpty()
    }

    private fun checkEmpty() {
        binding.textRecyclerViewSearchedQuizEmpty.visibility =
            if (this.quizAdapter.itemCount == 0) View.VISIBLE else View.GONE
    }
}