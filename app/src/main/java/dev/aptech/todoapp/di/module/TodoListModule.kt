package dev.aptech.todoapp.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.aptech.todoapp.di.ViewModelKey
import dev.aptech.todoapp.ui.screen.todolist.TodoListViewModel

@Module
interface TodoListModule {
    @Binds
    @IntoMap
    @ViewModelKey(TodoListViewModel::class)
    fun bindTodoListViewModel(todoListViewModel: TodoListViewModel): ViewModel
}