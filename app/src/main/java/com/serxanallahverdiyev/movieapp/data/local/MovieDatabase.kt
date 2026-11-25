package com.serxanallahverdiyev.movieapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.serxanallahverdiyev.movieapp.data.local.dao.FavoriteDao
import com.serxanallahverdiyev.movieapp.data.local.dao.GenreDao
import com.serxanallahverdiyev.movieapp.data.local.dao.MovieDao
import com.serxanallahverdiyev.movieapp.data.local.dao.TvShowDao
import com.serxanallahverdiyev.movieapp.data.local.entity.FavoriteEntity
import com.serxanallahverdiyev.movieapp.data.local.entity.GenreEntity
import com.serxanallahverdiyev.movieapp.data.local.entity.MovieEntity
import com.serxanallahverdiyev.movieapp.data.local.entity.TvShowEntity

@Database(
    entities = [
        FavoriteEntity::class,
        MovieEntity::class,
        TvShowEntity::class,
        GenreEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract val favoriteDao: FavoriteDao
    abstract val movieDao: MovieDao
    abstract val tvShowDao: TvShowDao
    abstract val genreDao: GenreDao
}
