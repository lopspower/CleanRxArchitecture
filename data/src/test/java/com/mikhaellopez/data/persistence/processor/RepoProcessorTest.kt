package com.mikhaellopez.data.persistence.processor

import com.mikhaellopez.data.persistence.dao.RepoDao
import com.mikhaellopez.data.persistence.entity.RepoEntity
import com.mikhaellopez.domain.exception.PersistenceException
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class RepoProcessorTest {

    @Mock
    private lateinit var dao: RepoDao

    private lateinit var processor: RepoProcessor

    @Before
    fun setup() {
        processor = RepoProcessor(dao)
    }

    @Test
    fun insertRepo() {
        val rowID = 1L
        val entity = mock<RepoEntity>()

        whenever(dao.insert(entity)).thenReturn(rowID)

        processor.insert(entity).test()
            .assertResult()
    }

    @Test
    fun insertRepoFail() {
        val entity = mock<RepoEntity>()

        whenever(dao.insert(entity)).thenReturn(0L)

        processor.insert(entity).test()
            .assertError(PersistenceException::class.java)
    }

    @Test
    fun deleteRepo() {
        val nbEntityDeleted = 1
        val entity = mock<RepoEntity>()

        whenever(dao.delete(entity)).thenReturn(nbEntityDeleted)

        processor.delete(entity).test()
            .assertResult()
    }

    @Test
    fun deleteRepoFail() {
        val entity = mock<RepoEntity>()

        whenever(dao.delete(entity)).thenReturn(0)

        processor.delete(entity).test()
            .assertError(PersistenceException::class.java)
    }

    @Test
    fun getRepo() {
        val id = 1L
        val entity = mock<RepoEntity>()

        whenever(dao.get(id)).thenReturn(entity)

        processor.get(id).test()
            .assertValueCount(1)
            .assertResult(entity)
    }

    @Test
    fun getRepoEmpty() {
        val id = 1L

        whenever(dao.get(id)).thenReturn(null)

        processor.get(id).test()
            .assertResult()
    }

    @Test
    fun getListRepo() {
        val userName = "userName"
        val repoList = mock<List<RepoEntity>>()

        whenever(dao.getAll(userName)).thenReturn(repoList)

        processor.getAll(userName).test()
            .assertValueCount(1)
            .assertResult(repoList)
    }

    @Test
    fun updateIsFavorite() {
        val id = 1L
        val isFavorite = true
        val nbEntityUpdated = 1

        whenever(dao.updateIsFavorite(id, isFavorite)).thenReturn(nbEntityUpdated)

        processor.updateIsFavorite(id, isFavorite).test()
            .assertResult()
    }

    @Test
    fun updateIsFavoriteFail() {
        val id = 1L
        val isFavorite = true

        whenever(dao.updateIsFavorite(id, isFavorite)).thenReturn(0)

        processor.updateIsFavorite(id, isFavorite).test()
            .assertError(PersistenceException::class.java)
    }

}