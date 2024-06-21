package dev.aptech.todoapp.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.aptech.todoapp.di.module.TodoListModule
import dev.aptech.todoapp.ui.screen.todolist.TodoListFragment

@Module
interface TodoListFragmentBuilder {
    @ContributesAndroidInjector(modules = [TodoListModule::class])
    fun contributeTodoListFragment(): TodoListFragment
}