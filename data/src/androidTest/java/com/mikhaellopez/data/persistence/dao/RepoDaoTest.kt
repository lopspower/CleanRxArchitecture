package com.mikhaellopez.data.persistence.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.mikhaellopez.data.persistence.AppDatabase
import com.mikhaellopez.data.persistence.DatabaseFactoryTest
import com.mikhaellopez.data.persistence.entity.RepoEntity
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Single
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class RepoDaoTest {

    companion object {
        private const val USER_NAME = "userName"
    }

    private lateinit var database: AppDatabase

    @Before
    fun initDb() {
        database =
            DatabaseFactoryTest.getDatabase(InstrumentationRegistry.getInstrumentation().context)
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insert() {
        val id = 1L
        val entity = RepoEntity(id, "name", "description", "http://myrepo.com", false, USER_NAME)

        assert(database.repoDao().insert(entity) > 0)

        Maybe.fromCallable { database.repoDao().get(id) }.test()
            .assertNoErrors()
            .assertValueCount(1)
            .assertValue { it.compareTo(entity) }
    }

    @Test
    fun delete() {
        val id = 1L
        val entity = RepoEntity(id, "name", "description", "http://myrepo.com", false, USER_NAME)

        assert(database.repoDao().insert(entity) > 0)

        Maybe.fromCallable { database.repoDao().get(id) }.test()
            .assertNoErrors()
            .assertValueCount(1)
            .assertValue { it.compareTo(entity) }

        assert(database.repoDao().delete(entity) == 1)

        Maybe.fromCallable { database.repoDao().get(id) }.test()
            .assertNoErrors()
            .assertResult()
    }

    @Test
    fun get() {
        // Check table is empty
        val id = 1L

        Maybe.fromCallable { database.repoDao().get(id) }.test()
            .assertResult()

        // Insert a repo & select it
        val entity = RepoEntity(id, "name", "description", "http://myrepo.com", false, USER_NAME)

        assert(database.repoDao().insert(entity) > 0)

        Maybe.fromCallable { database.repoDao().get(id) }.test()
            .assertNoErrors()
            .assertValueCount(1)
            .assertValue { it.compareTo(entity) }
    }

    @Test
    fun getAll() {
        // Check table is empty
        Single.fromCallable { database.repoDao().getAll(USER_NAME) }.test()
            .assertNoErrors()
            .assertValueCount(1)
            .assertValue { it.isEmpty() }

        // Insert two repos & select it
        val entity1 = RepoEntity(1, "name", "description", "http://myrepo.com", false, USER_NAME)
        val entity2 = RepoEntity(2, "name", "description", "http://myrepo.com", false, USER_NAME)

        assert(database.repoDao().insert(entity1) > 0)
        assert(database.repoDao().insert(entity2) > 0)

        Single.fromCallable { database.repoDao().getAll(USER_NAME) }.test()
            .assertNoErrors()
            .assertValueCount(1)
            .assertValue {
                it.size == 2
                        && it[0].compareTo(entity1)
                        && it[1].compareTo(entity2)
            }
    }

    @Test
    fun updateIsFavorite() {
        val id = 1L
        val entity = RepoEntity(id, "name", "description", "http://myrepo.com", false, USER_NAME)

        assert(database.repoDao().insert(entity) > 0)

        Maybe.fromCallable { database.repoDao().get(id) }.test()
            .assertNoErrors()
            .assertValueCount(1)
            .assertValue { !it!!.isFavorite } // False here

        assert(database.repoDao().updateIsFavorite(id, !entity.isFavorite) == 1)

        Maybe.fromCallable { database.repoDao().get(id) }.test()
            .assertNoErrors()
            .assertValueCount(1)
            .assertValue { it!!.isFavorite } // True here after update
    }

    private fun RepoEntity?.compareTo(entity: RepoEntity): Boolean =
        this?.run {
            id == entity.id
                    && name == entity.name
                    && description == entity.description
                    && url == entity.url
                    && isFavorite == entity.isFavorite
                    && userName == entity.userName
        } ?: false

}