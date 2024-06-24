package dev.aptech.todoapp

import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dev.aptech.todoapp.di.AppComponent
import dev.aptech.todoapp.di.DaggerAppComponent

class TodoApp: DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent
            .builder()
            .application(this)
            .build()
}