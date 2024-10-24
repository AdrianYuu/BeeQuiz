package edu.bluejack23_2.beequiz.fragment.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import edu.bluejack23_2.beequiz.adapter.ModifiedQuizAdapter
import edu.bluejack23_2.beequiz.databinding.FragmentMyQuizBinding
import edu.bluejack23_2.beequiz.helper.FragmentHelper
import edu.bluejack23_2.beequiz.`interface`.IFragment
import edu.bluejack23_2.beequiz.model.Quiz
import edu.bluejack23_2.beequiz.util.QRCode
import edu.bluejack23_2.beequiz.view_model.MyQuizViewModel

class MyQuizFragment : Fragment(), IFragment {

    private var _binding: FragmentMyQuizBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MyQuizViewModel
    private lateinit var quizAdapter: ModifiedQuizAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this._binding = FragmentMyQuizBinding.inflate(inflater, container, false)
        this.viewModel = MyQuizViewModel()
        this.init()
        this.setEventListener()
        return binding.root
    }

    override fun init() {
        viewModel.getQuizzesByNIM(viewModel.getCurrentUser().nim!!) { quizzes ->
            if (quizzes != null) {
                setupAdapter(quizzes)
            }
        }
    }

    private fun setupAdapter(quizzes: ArrayList<Quiz>) {
        this.quizAdapter = ModifiedQuizAdapter(quizzes,
            onClick = { position ->
                binding.wrapperPopUpQr.visibility = View.VISIBLE
                binding.textQuizCode.text = quizzes[position].code
                binding.imageQrCode.setImageBitmap(QRCode.encodeAsBitmap(quizzes[position].code!!))
            }
        )
        binding.recyclerViewMyQuiz.adapter = quizAdapter
        if(isAdded) binding.recyclerViewMyQuiz.layoutManager = LinearLayoutManager(requireContext())
        this.checkEmpty()
    }

    override fun setEventListener() {
        binding.buttonCreateNewQuiz.setOnClickListener {
            FragmentHelper.replaceFragment(
                requireActivity().supportFragmentManager,
                CreateQuizFragment()
            )
        }

        binding.buttonClosePopUpQr.setOnClickListener {
            binding.wrapperPopUpQr.visibility = View.GONE
        }
    }

    private fun checkEmpty() {
        binding.textRecyclerViewMyQuizEmpty.visibility =
            if (this.quizAdapter.itemCount == 0) View.VISIBLE else View.GONE
    }

}