package dev.aptech.todoapp.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dev.aptech.todoapp.TodoApp
import dev.aptech.todoapp.di.builder.MainActivityBuilder
import dev.aptech.todoapp.di.builder.TodoEditFragmentBuilder
import dev.aptech.todoapp.di.builder.TodoListFragmentBuilder
import dev.aptech.todoapp.di.module.RepositoryModule
import dev.aptech.todoapp.di.module.TodoListModule
import dev.aptech.todoapp.di.module.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    RepositoryModule::class,
    ViewModelModule::class,
    TodoListFragmentBuilder::class,
    TodoEditFragmentBuilder::class,
    MainActivityBuilder::class
])
interface AppComponent: AndroidInjector<TodoApp> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}