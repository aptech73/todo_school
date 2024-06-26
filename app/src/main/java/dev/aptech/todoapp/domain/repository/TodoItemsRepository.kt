package dev.aptech.todoapp.domain.repository

import android.content.IntentSender.OnFinished
import dev.aptech.todoapp.domain.model.TodoItem
import dev.aptech.todoapp.domain.model.TodoItemImpl
import kotlinx.coroutines.flow.Flow

interface TodoItemsRepository {

    fun getTodoItems(): Flow<List<TodoItem>>

    suspend fun getItemById(id: String): TodoItem

    suspend fun deleteItemById(id: String)

    suspend fun insertItem(todo: TodoItemImpl)

    suspend fun updateFinished(id: String, isFinished: Boolean)
}