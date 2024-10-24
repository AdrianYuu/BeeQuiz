package edu.bluejack23_2.beequiz.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.bluejack23_2.beequiz.databinding.CardHistoryQuizBinding
import edu.bluejack23_2.beequiz.model.Quiz
import edu.bluejack23_2.beequiz.model.User

class HistoryQuizAdapter(
    private var quizzes: ArrayList<Quiz>,
    private val user: User
) : RecyclerView.Adapter<HistoryQuizAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: CardHistoryQuizBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(quiz: Quiz) {
            val maxScore =
                quiz.results!!.filter { it.user!!.nim == user.nim }.maxOfOrNull { it.score ?: 0 }
            binding.cardHistoryQuestionTitle.text = quiz.title
            binding.cardHistoryQuestionTotalQuestions.text =
                quiz.questions!!.size.toString() + " Question(s)"
            binding.cardHistoryQuestionScore.text = "Your Result: " + maxScore.toString()
            binding.cardHistoryQuestionColor.setBackgroundColor(Color.parseColor(quiz.color));
        }

        fun setEventListener(position: Int) {
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryQuizAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardHistoryQuizBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryQuizAdapter.ViewHolder, position: Int) {
        holder.bind(quizzes[position])
        holder.setEventListener(position)
    }

    override fun getItemCount(): Int {
        return quizzes.size
    }

}