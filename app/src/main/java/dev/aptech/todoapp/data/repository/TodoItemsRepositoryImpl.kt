package dev.aptech.todoapp.data.repository

import android.util.Log
import dev.aptech.todoapp.domain.model.Importance
import dev.aptech.todoapp.domain.model.TodoItem
import dev.aptech.todoapp.domain.model.TodoItemEmpty
import dev.aptech.todoapp.domain.model.TodoItemImpl
import dev.aptech.todoapp.domain.repository.TodoItemsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Calendar
import java.util.Date

private const val TAG = "TodoItemsRepository"

private const val INVALID_INDEX = -1

class TodoItemsRepositoryImpl: TodoItemsRepository {

    private var todos = MutableStateFlow ( listOf(
            TodoItemImpl("1", "Купить что-то adlaskdlmaalwdmklawmlkawmdlkawmadawawdadw", Importance.NORMAL, isFinished = false, createDate = Date()),
            TodoItemImpl("2", "Купить что-то", Importance.NORMAL, isFinished = false, createDate = Date()),
            TodoItemImpl("3", "Купить что-то", Importance.NORMAL, isFinished = false, createDate = Date()),
            TodoItemImpl("4", "Купить что-то", Importance.NORMAL, isFinished = false, createDate = Date()),
            TodoItemImpl("5", "Купить что-то", Importance.HIGH, isFinished = false, createDate = Date()),
            TodoItemImpl("6", "Купить что-то", Importance.NORMAL, isFinished = false, createDate = Date(), deadline = Date(Calendar.getInstance().timeInMillis)),
            TodoItemImpl("7", "Купить что-то", Importance.NORMAL, isFinished = false, createDate = Date()),
            TodoItemImpl("8", "Купить что-то", Importance.NORMAL, isFinished = false, createDate = Date()),
            TodoItemImpl("9", "Купить что-то", Importance.HIGH, isFinished = false, createDate = Date()),
            TodoItemImpl("10", "Купить что-то", Importance.NORMAL, isFinished = false, createDate = Date()),
            TodoItemImpl("111", "Купить что-то", Importance.NORMAL, isFinished = false, createDate = Date()),
            TodoItemImpl("112", "Купить что-то", Importance.HIGH, isFinished = false, createDate = Date(), deadline = Date(Calendar.getInstance().timeInMillis)),
            TodoItemImpl("113", "Купить что-то", Importance.LOW, isFinished = false, createDate = Date()),
            TodoItemImpl("114", "Купить что-то", Importance.LOW, isFinished = false, createDate = Date()),
            TodoItemImpl("115", "Купить что-то", Importance.NORMAL, isFinished = true, createDate = Date()),
            TodoItemImpl("116", "Купить что-то", Importance.NORMAL, isFinished = true, createDate = Date()),
            TodoItemImpl("117", "Купить что-то", Importance.NORMAL, isFinished = false, createDate = Date()),
            TodoItemImpl("118", "Купить что-то", Importance.NORMAL, isFinished = false, createDate = Date()),
        )
    )


    override fun getTodoItems(): Flow<List<TodoItem>> = todos

    override suspend fun getItemById(id: String): TodoItem {
        val index = todos.value.indexOfFirst { it.id == id }
        return when (index) {
            INVALID_INDEX -> TodoItemEmpty
            else -> todos.value[index]
        }
    }

    override suspend fun deleteItemById(id: String) {
        Log.d(TAG, "[deleteItemById] todoId: $id")
        val index = todos.value.indexOfFirst { it.id == id }
        when (index) {
            INVALID_INDEX -> {}
            else -> todos.value -= todos.value.first { it.id == id }
        }
    }

    override suspend fun insertItem(todo: TodoItemImpl) {
        Log.d(TAG, "[insertItem] todo: $todo")
        val index = todos.value.indexOfFirst { it.id == todo.id }
        when (index) {
            INVALID_INDEX -> todos.value += todo
            else -> todos.value = todos.value.toMutableList().apply { this[index] = todo }.toList()
        }
    }

    override suspend fun updateFinished(id: String, isFinished: Boolean) {
        Log.d(TAG, "[updateFinished] todoId: $id")
        val index = todos.value.indexOfFirst { it.id == id }
        when (index) {
            INVALID_INDEX -> {  }
            else -> todos.value = todos.value.toMutableList().apply { this[index] = this[index].copy(isFinished = isFinished) }.toList()
        }
    }
}