package dev.aptech.todoapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.aptech.todoapp.data.repository.TodoItemsRepositoryImpl
import dev.aptech.todoapp.domain.repository.TodoItemsRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideTodoItemsRepository(todoItemsRepositoryImpl: TodoItemsRepositoryImpl): TodoItemsRepository
}