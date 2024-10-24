package edu.bluejack23_2.beequiz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.bluejack23_2.beequiz.databinding.CardLeaderboardBinding
import edu.bluejack23_2.beequiz.model.Scoreboard


class LeaderboardAdapter(
    private var scoreboardList: ArrayList<Scoreboard>
) : RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: CardLeaderboardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(score: Scoreboard, position: Int) {
            binding.cardLeaderboardName.text = score.user!!.name
            binding.cardLeaderboardScore.text = score.score.toString()
            binding.cardLeaderboardNumber.text = (position + 1).toString()
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LeaderboardAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardLeaderboardBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return scoreboardList.size
    }

    override fun onBindViewHolder(holder: LeaderboardAdapter.ViewHolder, position: Int) {
        holder.bind(scoreboardList[position], position)
    }

    fun updateData(newQuizzes: ArrayList<Scoreboard>) {
        this.scoreboardList = newQuizzes
        notifyDataSetChanged()
    }

}