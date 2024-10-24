package edu.bluejack23_2.beequiz.fragment.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import edu.bluejack23_2.beequiz.adapter.HistoryQuizAdapter
import edu.bluejack23_2.beequiz.databinding.FragmentHistoryBinding
import edu.bluejack23_2.beequiz.`interface`.IFragment
import edu.bluejack23_2.beequiz.model.Quiz
import edu.bluejack23_2.beequiz.view_model.HistoryViewModel

class HistoryFragment : Fragment(), IFragment {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HistoryViewModel
    private lateinit var quizAdapter: HistoryQuizAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this._binding = FragmentHistoryBinding.inflate(inflater, container, false)
        this.viewModel = HistoryViewModel()
        this.init()
        this.setEventListener()
        return binding.root
    }

    override fun init() {
    }

    override fun setEventListener() {
        binding.buttonFilter.setOnClickListener {
            viewModel.fetchQuiz(
                viewModel.getCurrentUser().nim!!,
                binding.inputMonth.text.toString(),
                binding.inputYear.text.toString(),
                requireActivity()
            ) { quizzes ->
                if (quizzes != null) setupAdapter(quizzes)
            }
        }
    }

    private fun setupAdapter(quizzes: ArrayList<Quiz>) {
        this.quizAdapter = HistoryQuizAdapter(quizzes, viewModel.getCurrentUser())
        binding.recyclerViewMyHistoryQuiz.adapter = quizAdapter
        if(isAdded) binding.recyclerViewMyHistoryQuiz.layoutManager = LinearLayoutManager(requireContext())
        this.checkEmpty()
    }

    private fun checkEmpty() {
        binding.textRecyclerViewMyHistoryEmpty.visibility =
            if (this.quizAdapter.itemCount == 0) View.VISIBLE else View.GONE
    }

}