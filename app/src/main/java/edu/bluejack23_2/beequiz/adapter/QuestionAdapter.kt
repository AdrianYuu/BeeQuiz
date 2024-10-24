package edu.bluejack23_2.beequiz.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import edu.bluejack23_2.beequiz.databinding.CardCreatedQuestionBinding
import edu.bluejack23_2.beequiz.model.Option
import edu.bluejack23_2.beequiz.model.Question

class QuestionAdapter(
    private val questions: ArrayList<Question>,
    private val onDeleteClick: (Int) -> Unit,
    private val onUpdateClick: (Int, Question) -> Unit,
) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: CardCreatedQuestionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var correctOption: CheckBox? = null

        fun bind(question: Question, position: Int) {
            binding.inputQuestionTitle.setText(question.title)
            binding.inputOptionOne.setText(question.options!![0].title)
            binding.inputOptionTwo.setText(question.options!![1].title)
            binding.inputOptionThree.setText(question.options!![2].title)
            binding.inputOptionFour.setText(question.options!![3].title)
            binding.checkBoxOptionOne.isChecked = question.options!![0].isCorrect!!
            binding.checkBoxOptionTwo.isChecked = question.options!![1].isCorrect!!
            binding.checkBoxOptionThree.isChecked = question.options!![2].isCorrect!!
            binding.checkBoxOptionFour.isChecked = question.options!![3].isCorrect!!
            binding.textQuestionHeader.text = String.format("Question #%d", position + 1)

            correctOption = when {
                binding.checkBoxOptionOne.isChecked -> binding.checkBoxOptionOne
                binding.checkBoxOptionTwo.isChecked -> binding.checkBoxOptionTwo
                binding.checkBoxOptionThree.isChecked -> binding.checkBoxOptionThree
                binding.checkBoxOptionFour.isChecked -> binding.checkBoxOptionFour
                else -> null
            }
        }

        private fun resetCheckedExcept(checkedCheckBox: CheckBox) {
            binding.checkBoxOptionOne.isChecked = (checkedCheckBox == binding.checkBoxOptionOne)
            binding.checkBoxOptionTwo.isChecked = (checkedCheckBox == binding.checkBoxOptionTwo)
            binding.checkBoxOptionThree.isChecked = (checkedCheckBox == binding.checkBoxOptionThree)
            binding.checkBoxOptionFour.isChecked = (checkedCheckBox == binding.checkBoxOptionFour)
            this.correctOption = checkedCheckBox
        }

        fun setEventListener(position: Int) {
            binding.buttonDeleteQuestion.setOnClickListener {
                onDeleteClick(position)
                notifyDataSetChanged()
            }

            binding.buttonUpdateQuestion.setOnClickListener {
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
                onUpdateClick(
                    position,
                    Question(binding.inputQuestionTitle.text.toString(), options)
                )
                notifyDataSetChanged()
            }

            binding.textQuestionHeader.setOnClickListener {
                binding.questionWrapper.visibility =
                    if (binding.questionWrapper.isVisible) View.GONE else View.VISIBLE
            }
            binding.checkBoxOptionOne.setOnClickListener { resetCheckedExcept(binding.checkBoxOptionOne) }
            binding.checkBoxOptionTwo.setOnClickListener { resetCheckedExcept(binding.checkBoxOptionTwo) }
            binding.checkBoxOptionThree.setOnClickListener { resetCheckedExcept(binding.checkBoxOptionThree) }
            binding.checkBoxOptionFour.setOnClickListener { resetCheckedExcept(binding.checkBoxOptionFour) }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardCreatedQuestionBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestionAdapter.ViewHolder, position: Int) {
        holder.bind(questions[position], position)
        holder.setEventListener(position)
    }

    override fun getItemCount(): Int {
        return questions.size
    }

}