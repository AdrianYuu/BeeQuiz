package edu.bluejack23_2.beequiz.fragment.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import edu.bluejack23_2.beequiz.adapter.QuestionAdapter
import edu.bluejack23_2.beequiz.databinding.FragmentCreateQuizBinding
import edu.bluejack23_2.beequiz.helper.FragmentHelper
import edu.bluejack23_2.beequiz.`interface`.IFragment
import edu.bluejack23_2.beequiz.model.Option
import edu.bluejack23_2.beequiz.model.Question
import edu.bluejack23_2.beequiz.view_model.CreateQuizViewModel

class CreateQuizFragment : Fragment(), IFragment {

    private var _binding: FragmentCreateQuizBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CreateQuizViewModel
    private lateinit var questionAdapter: QuestionAdapter

    private var correctOption: CheckBox? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this._binding = FragmentCreateQuizBinding.inflate(inflater, container, false)
        this.viewModel = CreateQuizViewModel()
        this.init()
        this.setEventListener()
        return binding.root
    }

    override fun init() {
        this.questionAdapter = QuestionAdapter(viewModel.getQuestions(),
            onDeleteClick = { position ->
                viewModel.deleteQuestion(position, requireActivity())
                this.checkEmpty()
            },
            onUpdateClick = { position, question ->
                viewModel.updateQuestion(position, question, requireActivity())
            }
        )

        binding.recyclerViewQuestions.adapter = questionAdapter
        if(isAdded) binding.recyclerViewQuestions.layoutManager = LinearLayoutManager(requireContext())
        this.checkEmpty()
    }

    override fun setEventListener() {
        binding.buttonAddQuestion.setOnClickListener {
            if (!binding.formQuestion.isVisible) binding.formQuestion.visibility = View.VISIBLE
        }

        binding.buttonDone.setOnClickListener {
            val options = ArrayList<Option>()
            options.add(
                Option(
                    binding.inputOptionOne.text.toString(),
                    binding.checkBoxOptionOne == correctOption
                )
            )
            options.add(
                Option(
                    binding.inputOptionTwo.text.toString(),
                    binding.checkBoxOptionTwo == correctOption
                )
            )
            options.add(
                Option(
                    binding.inputOptionThree.text.toString(),
                    binding.checkBoxOptionThree == correctOption
                )
            )
            options.add(
                Option(
                    binding.inputOptionFour.text.toString(),
                    binding.checkBoxOptionFour == correctOption
                )
            )

            if (viewModel.saveQuestion(
                    Question(
                        binding.inputQuestionTitle.text.toString(),
                        options
                    ), requireActivity()
                )
            ) {
                this.clearInputFields()
                this.questionAdapter.notifyDataSetChanged()
                binding.formQuestion.visibility = View.GONE
                binding.textRecyclerViewQuestionsEmpty.visibility = View.GONE
            }
        }

        binding.buttonSaveQuiz.setOnClickListener {
            if (viewModel.createQuiz(binding.inputQuizTitle.text.toString(), requireActivity())) {
                FragmentHelper.replaceFragment(
                    requireActivity().supportFragmentManager,
                    MyQuizFragment()
                )
            }
        }

        binding.checkBoxOptionOne.setOnClickListener { resetCheckedExcept(binding.checkBoxOptionOne) }
        binding.checkBoxOptionTwo.setOnClickListener { resetCheckedExcept(binding.checkBoxOptionTwo) }
        binding.checkBoxOptionThree.setOnClickListener { resetCheckedExcept(binding.checkBoxOptionThree) }
        binding.checkBoxOptionFour.setOnClickListener { resetCheckedExcept(binding.checkBoxOptionFour) }
    }

    private fun resetCheckedExcept(checkedCheckBox: CheckBox) {
        this.correctOption = checkedCheckBox
        binding.checkBoxOptionOne.isChecked = (checkedCheckBox == binding.checkBoxOptionOne)
        binding.checkBoxOptionTwo.isChecked = (checkedCheckBox == binding.checkBoxOptionTwo)
        binding.checkBoxOptionThree.isChecked = (checkedCheckBox == binding.checkBoxOptionThree)
        binding.checkBoxOptionFour.isChecked = (checkedCheckBox == binding.checkBoxOptionFour)
    }

    private fun clearInputFields() {
        binding.inputQuestionTitle.text.clear()
        binding.inputOptionOne.text.clear()
        binding.inputOptionTwo.text.clear()
        binding.inputOptionThree.text.clear()
        binding.inputOptionFour.text.clear()
        binding.checkBoxOptionOne.isChecked = false
        binding.checkBoxOptionTwo.isChecked = false
        binding.checkBoxOptionThree.isChecked = false
        binding.checkBoxOptionFour.isChecked = false
        this.correctOption = null
    }

    private fun checkEmpty() {
        binding.textRecyclerViewQuestionsEmpty.visibility =
            if (this.questionAdapter.itemCount == 0) View.VISIBLE else View.GONE
    }

}