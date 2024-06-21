package dev.aptech.todoapp.di.builder

import dagger.Module
import dagger.android.ContributesAndroidInjector
import dev.aptech.todoapp.di.module.MainModule
import dev.aptech.todoapp.ui.MainActivity

@Module
interface MainActivityBuilder {
    @ContributesAndroidInjector(modules = [MainModule::class])
    fun contributeMainActivity(): MainActivity
}