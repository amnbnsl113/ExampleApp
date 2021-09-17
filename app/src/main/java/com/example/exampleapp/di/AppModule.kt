package com.example.exampleapp.di


import android.app.Application
import androidx.room.Room
import com.example.exampleapp.data.local.AppDatabase
import com.example.exampleapp.data.remote.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providesDatabase(context: Application): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app-database"
        ).build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(): Retrofit {
        val baseUrl = "https://reqres.in/"
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    @Provides
    @Singleton
    fun providesUserService(retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }
}