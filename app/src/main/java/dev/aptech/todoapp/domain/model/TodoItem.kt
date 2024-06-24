package dev.aptech.todoapp.domain.model

import java.util.Date

sealed class TodoItem

data class TodoItemImpl(
    val id: String,
    val body: String,
    val importance: Importance,
    val deadline: Date? = null,
    val isFinished: Boolean,
    val createDate: Date,
    val changeDate: Date? = null
): TodoItem()

enum class Importance {
    LOW, NORMAL, HIGH
}

data object TodoItemEmpty: TodoItem()