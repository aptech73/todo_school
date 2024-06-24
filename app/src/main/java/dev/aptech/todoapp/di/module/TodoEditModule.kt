package dev.aptech.todoapp.di.module

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import dev.aptech.todoapp.di.ViewModelKey
import dev.aptech.todoapp.ui.screen.todoedit.TodoEditViewModel

@Module
interface TodoEditModule {
    @Binds
    @IntoMap
    @ViewModelKey(TodoEditViewModel::class)
    fun bindTodoListViewModel(todoEditViewModel: TodoEditViewModel): ViewModel
}