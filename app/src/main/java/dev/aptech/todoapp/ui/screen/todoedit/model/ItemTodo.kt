package dev.aptech.todoapp.ui.screen.todoedit.model

import dev.aptech.todoapp.domain.model.Importance
import java.util.Calendar
import java.util.Date

private const val EMPTY = ""

data class ItemTodo (
    val id: String,
    val body: String,
    val importance: Importance,
    val deadline: Date? = null,
    val isFinished: Boolean,
    val createDate: Date,
    val changeDate: Date? = null
) {
    companion object {
        fun default() = ItemTodo(
            id = hashCode().toString(),
            body = EMPTY,
            importance = Importance.NORMAL,
            isFinished = false,
            createDate = Calendar.getInstance().time
        )
    }
}