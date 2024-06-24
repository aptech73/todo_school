package dev.aptech.todoapp.ui.screen.todolist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import dev.aptech.todoapp.domain.model.TodoItem
import dev.aptech.todoapp.domain.model.TodoItemImpl
import dev.aptech.todoapp.domain.repository.TodoItemsRepository
import dev.aptech.todoapp.ui.screen.todolist.model.ItemTodo
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

class TodoListViewModel @Inject constructor(
    private val todoItemsRepository: TodoItemsRepository
): ViewModel() {

    private val itemsInternal: MutableLiveData<List<ItemTodo>> = MutableLiveData()
    val items = itemsInternal

    private val visibilityInternal: MutableLiveData<Boolean> = MutableLiveData(true)
    val visibility = visibilityInternal

    init {
        observeTodoList()
    }

    private fun observeTodoList() {
        viewModelScope.launch {
            todoItemsRepository.getTodoItems()
                .catch {  }
                .combine (visibility.asFlow()) { items, visibility ->
                    Pair(items, visibility)
                }
                .collect {
                    if (it.second) itemsInternal.value = mapToItems(it.first)
                    else itemsInternal.value = mapToItems(it.first).filter { !it.isFinished }
                }
        }
    }

    fun onVisibilityClick() {
        visibilityInternal.value = visibilityInternal.value?.not()
    }

    fun removeTodo(id: String) {
        viewModelScope.launch {
            todoItemsRepository.deleteItemById(id)
        }
    }

    fun setFinishedTodo(id: String, isFinished: Boolean) {
        viewModelScope.launch {
            todoItemsRepository.updateFinished(id, isFinished)
        }
    }

    private fun mapToItems(items: List<TodoItem>) = items.run {
        map { (it as TodoItemImpl).mapToView() }
    }

    private fun TodoItemImpl.mapToView() = ItemTodo(
        id = id,
        todoBody = body,
        importance = importance,
        isFinished = isFinished,
        deadline = deadline
    )
}