package dev.aptech.todoapp.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.aptech.todoapp.di.module.TodoEditModule
import dev.aptech.todoapp.ui.screen.todoedit.TodoEditFragment

@Module
interface TodoEditFragmentBuilder {
    @ContributesAndroidInjector(modules = [TodoEditModule::class])
    fun contributeTodoListFragment(): TodoEditFragment
}