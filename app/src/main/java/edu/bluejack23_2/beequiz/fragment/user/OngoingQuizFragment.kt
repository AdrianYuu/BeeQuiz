package edu.bluejack23_2.beequiz.fragment.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import edu.bluejack23_2.beequiz.databinding.FragmentOngoingQuizBinding
import edu.bluejack23_2.beequiz.helper.FragmentHelper
import edu.bluejack23_2.beequiz.`interface`.IFragment
import edu.bluejack23_2.beequiz.view_model.OngoingQuizViewModel

class OngoingQuizFragment : Fragment(), IFragment {

    private var _binding: FragmentOngoingQuizBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: OngoingQuizViewModel

    private var correctOption: CheckBox? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this._binding = FragmentOngoingQuizBinding.inflate(inflater, container, false)
        this.viewModel = OngoingQuizViewModel()
        this.init()
        this.setEventListener()
        return binding.root
    }

    override fun init() {
        this.fetchQuestion()
        this.checkLastQuestion()
    }

    override fun setEventListener() {
        binding.buttonNextQuestion.setOnClickListener {
            viewModel.updateAnswer(this.getAnswer())
            viewModel.setCurrentQuestionNumberIdx(viewModel.getCurrentQuestionNumberIdx() + 1)
            this.fetchQuestion()
            this.checkLastQuestion()
            this.resetAnswer()
            this.setAnswer(viewModel.getCurrentQuestionAnswer())
        }

        binding.buttonBackQuestion.setOnClickListener {
            viewModel.updateAnswer(this.getAnswer())
            viewModel.setCurrentQuestionNumberIdx(viewModel.getCurrentQuestionNumberIdx() - 1)
            this.fetchQuestion()
            this.checkLastQuestion()
            this.resetAnswer()
            this.setAnswer(viewModel.getCurrentQuestionAnswer())
        }

        binding.buttonFinalize.setOnClickListener {
            viewModel.updateAnswer(this.getAnswer())
            binding.wrapperPopUpFinalize.visibility = View.VISIBLE
        }

        binding.buttonYes.setOnClickListener {
            viewModel.addResult()
            FragmentHelper.replaceFragment(
                requireActivity().supportFragmentManager,
                FinishQuizFragment()
            )
        }

        binding.buttonNo.setOnClickListener {
            binding.wrapperPopUpFinalize.visibility = View.GONE
        }

        binding.checkBoxOptionOneAnswer.setOnClickListener { resetCheckedExcept(binding.checkBoxOptionOneAnswer) }
        binding.checkBoxOptionTwoAnswer.setOnClickListener { resetCheckedExcept(binding.checkBoxOptionTwoAnswer) }
        binding.checkBoxOptionThreeAnswer.setOnClickListener { resetCheckedExcept(binding.checkBoxOptionThreeAnswer) }
        binding.checkBoxOptionFourAnswer.setOnClickListener { resetCheckedExcept(binding.checkBoxOptionFourAnswer) }
    }

    private fun fetchQuestion() {
        binding.textQuestion.text = viewModel.getCurrentQuestion().title
        binding.textCurrentQuestionNumber.text = viewModel.getCurrentQuestionNumberIdx().toString()
        binding.inputOptionOneAnswer.setText(viewModel.getCurrentQuestion().options!![0].title)
        binding.inputOptionTwoAnswer.setText(viewModel.getCurrentQuestion().options!![1].title)
        binding.inputOptionThreeAnswer.setText(viewModel.getCurrentQuestion().options!![2].title)
        binding.inputOptionFourAnswer.setText(viewModel.getCurrentQuestion().options!![3].title)
        binding.textCurrentQuestionNumber.text =
            (viewModel.getCurrentQuestionNumberIdx() + 1).toString()
    }

    private fun checkLastQuestion() {
        if (viewModel.isLastQuestion()) {
            binding.buttonFinalize.visibility = View.VISIBLE
        } else {
            binding.buttonFinalize.visibility = View.GONE
        }
    }

    private fun resetCheckedExcept(checkedCheckBox: CheckBox) {
        this.correctOption = checkedCheckBox
        binding.checkBoxOptionOneAnswer.isChecked =
            (checkedCheckBox == binding.checkBoxOptionOneAnswer)
        binding.checkBoxOptionTwoAnswer.isChecked =
            (checkedCheckBox == binding.checkBoxOptionTwoAnswer)
        binding.checkBoxOptionThreeAnswer.isChecked =
            (checkedCheckBox == binding.checkBoxOptionThreeAnswer)
        binding.checkBoxOptionFourAnswer.isChecked =
            (checkedCheckBox == binding.checkBoxOptionFourAnswer)
    }

    private fun setAnswer(answer: Int) {
        when (answer) {
            0 -> binding.checkBoxOptionOneAnswer.isChecked = true
            1 -> binding.checkBoxOptionTwoAnswer.isChecked = true
            2 -> binding.checkBoxOptionThreeAnswer.isChecked = true
            3 -> binding.checkBoxOptionFourAnswer.isChecked = true
        }
    }

    private fun getAnswer(): Int {
        if (binding.checkBoxOptionOneAnswer.isChecked) return 0
        else if (binding.checkBoxOptionTwoAnswer.isChecked) return 1
        else if (binding.checkBoxOptionThreeAnswer.isChecked) return 2
        else if (binding.checkBoxOptionFourAnswer.isChecked) return 3
        return -1
    }

    private fun resetAnswer() {
        binding.checkBoxOptionOneAnswer.isChecked = false
        binding.checkBoxOptionTwoAnswer.isChecked = false
        binding.checkBoxOptionThreeAnswer.isChecked = false
        binding.checkBoxOptionFourAnswer.isChecked = false
    }

}