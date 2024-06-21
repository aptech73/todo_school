package dev.aptech.todoapp.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dev.aptech.todoapp.TodoApp
import javax.inject.Singleton

@Module
object AppModule {
    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application
}