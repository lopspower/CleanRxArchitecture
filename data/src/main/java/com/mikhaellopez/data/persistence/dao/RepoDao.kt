package com.mikhaellopez.data.persistence.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.mikhaellopez.data.persistence.dao.base.BaseDao
import com.mikhaellopez.data.persistence.entity.RepoEntity
import com.mikhaellopez.data.persistence.entity.RepoEntity.Companion.REPO_ID
import com.mikhaellopez.data.persistence.entity.RepoEntity.Companion.REPO_IS_FAVORITE
import com.mikhaellopez.data.persistence.entity.RepoEntity.Companion.REPO_TABLE
import com.mikhaellopez.data.persistence.entity.RepoEntity.Companion.REPO_USER_NAME

@Dao
abstract class RepoDao : BaseDao<RepoEntity> {

    /**
     * Select a repo by the id
     * @param id The repo id
     * @return A [RepoEntity]
     */
    @Query("SELECT * FROM $REPO_TABLE WHERE $REPO_ID = :id")
    abstract fun get(id: Long): RepoEntity?

    /**
     * Select all repos by the userName
     * @return A list of [RepoEntity] of all the repos in the table for user
     */
    @Query("SELECT * FROM $REPO_TABLE WHERE $REPO_USER_NAME = :userName")
    abstract fun getAll(userName: String): List<RepoEntity>

    /**
     * Update is favorite repo by the id
     * @param id The repo id
     * @param isFavorite If repo is favorite or not
     * @return A number of repo updated. This should always be `1`
     */
    @Query("UPDATE $REPO_TABLE SET $REPO_IS_FAVORITE = :isFavorite WHERE $REPO_ID = :id")
    abstract fun updateIsFavorite(id: Long, isFavorite: Boolean): Int

    @Transaction
    open fun insertAndDeleteInTransaction(newRepo: RepoEntity, oldRepo: RepoEntity) {
        // Anything inside this method runs in a single transaction.
        insert(newRepo)
        delete(oldRepo)
    }

}
