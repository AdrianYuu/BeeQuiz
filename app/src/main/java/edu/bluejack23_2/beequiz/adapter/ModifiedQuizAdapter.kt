package edu.bluejack23_2.beequiz.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.bluejack23_2.beequiz.databinding.CardModifiedQuizBinding
import edu.bluejack23_2.beequiz.model.Quiz

class ModifiedQuizAdapter(
    private val quizzes: ArrayList<Quiz>,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<ModifiedQuizAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: CardModifiedQuizBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(quiz: Quiz) {
            binding.cardModifiedQuestionTitle.text = quiz.title
            binding.cardModifiedQuestionTotalQuestions.text =
                quiz.questions!!.size.toString() + " Question(s)"
            binding.cardModifiedQuestionParticipants.text =
                "Joined by " + quiz.results!!.size.toString() + " Binusians"
            binding.cardModifiedQuestionColor.setBackgroundColor(Color.parseColor(quiz.color));
        }

        fun setEventListener(position: Int) {
            binding.buttonQrCode.setOnClickListener {
                onClick(position)
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ModifiedQuizAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardModifiedQuizBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ModifiedQuizAdapter.ViewHolder, position: Int) {
        holder.bind(quizzes[position])
        holder.setEventListener(position)
    }

    override fun getItemCount(): Int {
        return quizzes.size
    }

}