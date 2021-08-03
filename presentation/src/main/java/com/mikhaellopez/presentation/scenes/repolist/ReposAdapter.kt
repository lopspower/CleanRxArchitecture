package com.mikhaellopez.presentation.scenes.repolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mikhaellopez.domain.model.Repo
import com.mikhaellopez.presentation.R
import com.mikhaellopez.presentation.databinding.RepoItemBinding
import io.reactivex.rxjava3.subjects.PublishSubject

class ReposAdapter : RecyclerView.Adapter<ReposAdapter.ViewHolder>() {

    val repoClickIntent: PublishSubject<Repo> = PublishSubject.create()
    val repoFavoriteIntent: PublishSubject<Pair<Int, Repo>> = PublishSubject.create()

    private var data: MutableList<Repo> = mutableListOf()

    fun setData(newData: List<Repo>) {
        val diffResult = DiffUtil.calculateDiff(ReposDiffCallback(data, newData))
        data.clear()
        data.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setData(position: Int, repo: Repo) {
        data[position] = repo
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(RepoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(data[position], repoClickIntent, repoFavoriteIntent)

    override fun getItemCount() = data.size

    class ViewHolder(private val binding: RepoItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            repo: Repo,
            repoClickIntent: PublishSubject<Repo>,
            repoFavoriteIntent: PublishSubject<Pair<Int, Repo>>
        ) = with(itemView) {
            binding.textRepoName.text = repo.name
            binding.textRepoDescription.text = repo.description
            binding.imageFavoriteRepo.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    if (repo.isFavorite) R.drawable.heart else R.drawable.heart_outline
                )
            )

            setOnClickListener { repoClickIntent.onNext(repo) }
            binding.imageFavoriteRepo.setOnClickListener {
                repoFavoriteIntent.onNext(Pair(absoluteAdapterPosition, repo))
            }
        }
    }
}