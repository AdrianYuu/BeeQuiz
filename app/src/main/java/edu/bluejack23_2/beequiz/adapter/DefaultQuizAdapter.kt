package edu.bluejack23_2.beequiz.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.bluejack23_2.beequiz.databinding.CardDefaultQuizBinding
import edu.bluejack23_2.beequiz.model.Quiz

class DefaultQuizAdapter(
    private var quizzes: ArrayList<Quiz>,
    private val onClick: (Quiz) -> Unit
) : RecyclerView.Adapter<DefaultQuizAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: CardDefaultQuizBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(quiz: Quiz) {
            binding.cardDefaultQuestionTitle.text = quiz.title
            binding.cardDefaultQuestionTotalQuestions.text =
                quiz.questions!!.size.toString() + " Question(s)"
            binding.cardDefaultQuestionParticipants.text =
                "Joined by " + quiz.results!!.size.toString() + " Binusians"
            binding.cardDefaultQuestionColor.setBackgroundColor(Color.parseColor(quiz.color));
        }

        fun setEventListener(position: Int) {
            binding.cardDefault.setOnClickListener {
                onClick(quizzes[position])
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DefaultQuizAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardDefaultQuizBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DefaultQuizAdapter.ViewHolder, position: Int) {
        holder.bind(quizzes[position])
        holder.setEventListener(position)
    }

    override fun getItemCount(): Int {
        return quizzes.size
    }

    fun updateData(newQuizzes: ArrayList<Quiz>) {
        quizzes = newQuizzes
        notifyDataSetChanged()
    }

}