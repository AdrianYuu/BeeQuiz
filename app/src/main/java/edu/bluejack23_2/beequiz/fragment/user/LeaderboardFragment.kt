package edu.bluejack23_2.beequiz.fragment.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import edu.bluejack23_2.beequiz.adapter.LeaderboardAdapter
import edu.bluejack23_2.beequiz.databinding.FragmentLeaderboardBinding
import edu.bluejack23_2.beequiz.helper.FragmentHelper
import edu.bluejack23_2.beequiz.`interface`.IFragment
import edu.bluejack23_2.beequiz.model.Scoreboard
import edu.bluejack23_2.beequiz.view_model.LeaderboardViewModel

class LeaderboardFragment : Fragment(), IFragment {

    private var _binding: FragmentLeaderboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LeaderboardViewModel
    private lateinit var leaderboardAdapter: LeaderboardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this._binding = FragmentLeaderboardBinding.inflate(inflater, container, false)
        this.viewModel = LeaderboardViewModel()
        this.init()
        this.setEventListener()
        return binding.root
    }

    private fun setupAdapter(scores: ArrayList<Scoreboard>) {
        this.leaderboardAdapter = LeaderboardAdapter(scores)
        binding.recyclerViewLeaderboard.adapter = this.leaderboardAdapter
        if(isAdded) binding.recyclerViewLeaderboard.layoutManager = LinearLayoutManager(requireContext())
        this.checkEmpty()
    }

    private fun checkEmpty() {
        binding.textRecyclerViewLeaderboardEmpty.visibility =
            if (this.leaderboardAdapter.itemCount == 0) View.VISIBLE else View.GONE
    }

    override fun init() {
        viewModel.getLeaderboard { scores ->
            if (scores != null) {
                setupAdapter(scores)
            }
        }
    }

    override fun setEventListener() {
        binding.buttonLeaderboardBack.setOnClickListener {
            FragmentHelper.replaceFragment(
                requireActivity().supportFragmentManager,
                StartQuizFragment()
            )
        }
    }

}