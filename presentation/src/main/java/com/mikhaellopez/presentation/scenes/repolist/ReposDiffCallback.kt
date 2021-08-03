package com.mikhaellopez.presentation.scenes.repolist

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import com.mikhaellopez.domain.model.Repo

class ReposDiffCallback(
    private val oldList: List<Repo>,
    private val newList: List<Repo>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val (_, name, description, url, isFavorite, userName) = oldList[oldPosition]
        val (_, name1, description1, url1, isFavorite1, userName1) = newList[newPosition]
        return name == name1
                && description == description1
                && url == url1
                && isFavorite == isFavorite1
                && userName == userName1
    }

    @Nullable
    override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? =
        super.getChangePayload(oldPosition, newPosition)

}