package com.serxanallahverdiyev.movieapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.serxanallahverdiyev.movieapp.data.local.entity.TvShowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TvShowDao {
    @Query("SELECT * FROM tv_shows WHERE category = :category")
    fun getTvShowsByCategory(category: String): Flow<List<TvShowEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTvShows(tvShows: List<TvShowEntity>)

    @Query("DELETE FROM tv_shows WHERE category = :category")
    suspend fun deleteTvShowsByCategory(category: String)

    @Transaction
    suspend fun updateTvShowsByCategory(category: String, tvShows: List<TvShowEntity>) {
        deleteTvShowsByCategory(category)
        insertTvShows(tvShows)
    }
}
