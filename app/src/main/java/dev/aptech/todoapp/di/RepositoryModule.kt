package dev.aptech.todoapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.aptech.todoapp.data.repository.TodoItemsRepositoryImpl
import dev.aptech.todoapp.domain.repository.TodoItemsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideTodoItemsRepository(): TodoItemsRepository = TodoItemsRepositoryImpl()
}