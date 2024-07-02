package dev.aptech.todoapp.ui.screen.todoedit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.aptech.todoapp.domain.model.Importance
import dev.aptech.todoapp.domain.model.TodoItemEmpty
import dev.aptech.todoapp.domain.model.TodoItemImpl
import dev.aptech.todoapp.domain.repository.TodoItemsRepository
import dev.aptech.todoapp.ui.screen.todoedit.model.ItemTodo
import dev.aptech.todoapp.ui.screen.todoedit.validation.Validation
import dev.aptech.todoapp.ui.screen.todoedit.validation.ValidationEmpty
import dev.aptech.todoapp.ui.screen.todoedit.validation.ValidationOk
import dev.aptech.todoapp.util.SingleLiveEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TodoEditViewModel @Inject constructor(
    private val todoItemsRepository: TodoItemsRepository
): ViewModel() {

    private val currentItemInternal = MutableStateFlow<ItemTodo>(ItemTodo.default())
    val currentItem = currentItemInternal.asStateFlow()

    private val todoBodyValidationInternal = MutableLiveData<Validation>().apply { value = ValidationOk }
    val todoBodyValidation: LiveData<Validation> = todoBodyValidationInternal

    val onDeadLineCLick = SingleLiveEvent<Unit>()

    fun getItemById(id: String) {
        viewModelScope.launch {
            when (val item = todoItemsRepository.getItemById(id)) {
                is TodoItemEmpty -> {
                    currentItemInternal.value = ItemTodo.default()
                }
                is TodoItemImpl -> {
                    currentItemInternal.value = item.mapToView()
                }
            }
        }
    }

    fun saveTodo() {
        val todoBody = currentItemInternal.value.body.trim()

        if (todoBody.isEmpty()) {
            todoBodyValidationInternal.value = ValidationEmpty
            return
        }

        val todoItem = currentItemInternal.value.run {
            TodoItemImpl(
                id = id,
                body = todoBody,
                importance = importance,
                deadline = deadline,
                isFinished = isFinished,
                createDate = createDate,
                changeDate = Calendar.getInstance().time
            )
        }

        viewModelScope.launch {
            todoItemsRepository.insertItem(todoItem)
        }
    }

    fun deleteTodo(id: String) {
        viewModelScope.launch {
            todoItemsRepository.deleteItemById(id)
        }
    }

    fun onTodoBodyChanged(newValue: CharSequence) {
        val curValue = currentItemInternal.value
        newValue.toString().also {
            currentItemInternal.apply {
                todoBodyValidationInternal.value = when (it.isBlank()) {
                    true -> ValidationEmpty
                    else -> ValidationOk
                }
                value.let { item ->
                    if (item.body != it) {
                        value = curValue.copy(body = it)
                    }
                }
            }
        }
    }

    fun onDeadlineClick() {
        onDeadLineCLick.postValue(Unit)
    }

    fun disableDeadline() {
        currentItemInternal.value.run {
            copy(
                deadline = null
            ).let {
                currentItemInternal.value = it
            }
        }
    }

    fun enableDeadline(date: Date) {
        currentItemInternal.value.run {
            copy(
                deadline = date
            ).let {
                currentItemInternal.value = it
            }
        }
    }

    fun onImportanceClick(importance: Importance) {
        currentItemInternal.value.run {
            copy(
                importance = importance
            ).let {
                currentItemInternal.value = it
            }
        }
    }

    private fun TodoItemImpl.mapToView() = ItemTodo(
        id, body, importance, deadline, isFinished, createDate, changeDate
    )
}