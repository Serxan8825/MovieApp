package com.serxanallahverdiyev.movieapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.serxanallahverdiyev.movieapp.data.local.entity.GenreEntity

@Dao
interface GenreDao {
    @Query("SELECT * FROM genres WHERE type = :type")
    suspend fun getGenresByType(type: String): List<GenreEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGenres(genres: List<GenreEntity>)

    @Query("DELETE FROM genres WHERE type = :type")
    suspend fun deleteGenresByType(type: String)
}
