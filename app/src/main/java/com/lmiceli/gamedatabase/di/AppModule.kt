package com.lmiceli.gamedatabase.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import com.lmiceli.gamedatabase.data.GameMediator
import com.lmiceli.gamedatabase.data.local.AppDatabase
import com.lmiceli.gamedatabase.data.local.GameDao
import com.lmiceli.gamedatabase.data.remote.games.GameService
import com.lmiceli.gamedatabase.data.remote.https.RetrofitFactory
import com.lmiceli.gamedatabase.data.repository.GameRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(
        @ApplicationContext appContext: Context,
        moshi: Moshi,
    ): Retrofit {
        return RetrofitFactory.buildRetrofit(appContext, moshi)
    }

    @Provides
    fun providesMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    fun provideGameService(retrofit: Retrofit): GameService =
        retrofit.create(GameService::class.java)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideGameDao(db: AppDatabase) = db.gameDao()

    @ExperimentalPagingApi
    @Singleton
    @Provides
    fun provideRepository(
        remoteMediator: GameMediator,
        localDataSource: GameDao
    ) =
        GameRepository(remoteMediator, localDataSource)

    @ExperimentalPagingApi
    @Singleton
    @Provides
    fun provideMediator(
        service: GameService,
        database: AppDatabase,
    ) =
        GameMediator(service, database)

}
