package dev.aptech.todoapp.ui.screen.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aptech.todoapp.domain.model.TodoItem
import dev.aptech.todoapp.domain.model.TodoItemImpl
import dev.aptech.todoapp.domain.repository.TodoItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val todoItemsRepository: TodoItemsRepository
): ViewModel() {

    private val itemsInternal =  MutableStateFlow<List<TodoItemImpl>>(emptyList())
    val items = itemsInternal.asStateFlow()

    private val visibilityInternal = MutableStateFlow(true)
    val visibility = visibilityInternal.asStateFlow()

    init {
        observeTodoList()
    }

    private fun observeTodoList() {
        viewModelScope.launch(Dispatchers.IO) {
            todoItemsRepository.getTodoItems()
                .catch {  }
                .combine (visibility) { items, visibility ->
                    Pair(items, visibility)
                }
                .collect {
                    if (it.second) itemsInternal.value = mapToItems(it.first)
                    else itemsInternal.value = mapToItems(it.first).filter { todo -> !todo.isFinished }
                }
        }
    }

    fun onVisibilityClick() {
        visibilityInternal.value = visibilityInternal.value.not()
    }

    fun setFinishedTodo(id: String, isFinished: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            todoItemsRepository.updateFinished(id, isFinished)
        }
    }

    private fun mapToItems(items: List<TodoItem>) = items.run {
        map { (it as TodoItemImpl) }
    }
}