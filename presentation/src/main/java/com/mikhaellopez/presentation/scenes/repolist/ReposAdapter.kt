package com.mikhaellopez.presentation.scenes.repolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mikhaellopez.domain.model.Repo
import com.mikhaellopez.presentation.R
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.android.synthetic.main.repo_item.view.*

class ReposAdapter : RecyclerView.Adapter<ReposAdapter.ViewHolder>() {

    val repoClickIntent: PublishSubject<Repo> = PublishSubject.create()
    val repoFavoriteIntent: PublishSubject<Pair<Int, Repo>> = PublishSubject.create()

    var data: MutableList<Repo> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun setData(position: Int, repo: Repo) {
        data[position] = repo
        notifyItemChanged(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.repo_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(data[position], repoClickIntent, repoFavoriteIntent)

    override fun getItemCount() = data.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            repo: Repo,
            repoClickIntent: PublishSubject<Repo>,
            repoFavoriteIntent: PublishSubject<Pair<Int, Repo>>
        ) = with(itemView) {

            textRepoName.text = repo.name
            textRepoDescription.text = repo.description
            imageFavoriteRepo.setImageDrawable(
                ContextCompat.getDrawable(
                    context,
                    if (repo.isFavorite) R.drawable.heart else R.drawable.heart_outline
                )
            )

            setOnClickListener { repoClickIntent.onNext(repo) }
            imageFavoriteRepo.setOnClickListener {
                repoFavoriteIntent.onNext(
                    Pair(
                        adapterPosition,
                        repo
                    )
                )
            }
        }
    }
}