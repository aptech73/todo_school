package dev.aptech.todoapp.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dev.aptech.todoapp.data.repository.TodoItemsRepositoryImpl
import dev.aptech.todoapp.domain.repository.TodoItemsRepository
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideTodoItemsRepository(): TodoItemsRepository = TodoItemsRepositoryImpl()
}