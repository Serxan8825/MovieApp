package com.serxanallahverdiyev.movieapp.di

import android.content.Context
import androidx.room.Room
import com.serxanallahverdiyev.movieapp.data.local.MovieDatabase
import com.serxanallahverdiyev.movieapp.data.local.dao.FavoriteDao
import com.serxanallahverdiyev.movieapp.data.local.dao.GenreDao
import com.serxanallahverdiyev.movieapp.data.local.dao.MovieDao
import com.serxanallahverdiyev.movieapp.data.local.dao.TvShowDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "movie_db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideFavoriteDao(database: MovieDatabase): FavoriteDao {
        return database.favoriteDao
    }

    @Provides
    @Singleton
    fun provideMovieDao(database: MovieDatabase): MovieDao {
        return database.movieDao
    }

    @Provides
    @Singleton
    fun provideTvShowDao(database: MovieDatabase): TvShowDao {
        return database.tvShowDao
    }

    @Provides
    @Singleton
    fun provideGenreDao(database: MovieDatabase): GenreDao {
        return database.genreDao
    }
}
