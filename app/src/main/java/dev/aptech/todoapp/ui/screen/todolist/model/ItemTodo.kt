package dev.aptech.todoapp.ui.screen.todolist.model

import dev.aptech.todoapp.domain.model.Importance
import java.util.Date

data class ItemTodo (
    val id: String,
    val todoBody: String,
    val importance: Importance,
    val isFinished: Boolean,
    val deadline: Date? = null
)