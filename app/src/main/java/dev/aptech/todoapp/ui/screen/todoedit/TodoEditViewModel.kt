package dev.aptech.todoapp.ui.screen.todoedit

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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TodoEditViewModel @Inject constructor(
    private val todoItemsRepository: TodoItemsRepository
): ViewModel() {

    private val currentItemInternal = MutableStateFlow(ItemTodo.default())
    val currentItem = currentItemInternal.asStateFlow()

    private val todoBodyValidationInternal = MutableStateFlow<Validation>(ValidationOk)
    val todoBodyValidation = todoBodyValidationInternal.asStateFlow()

    private val datePickerVisibilityInternal = MutableStateFlow(false)
    val datePickerVisibility = datePickerVisibilityInternal.asStateFlow()

    private val menuVisibilityInternal = MutableStateFlow(false)
    val menuVisibility = menuVisibilityInternal.asStateFlow()

    fun getItemById(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
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
            todoBodyValidationInternal.update { ValidationEmpty }
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

        viewModelScope.launch(Dispatchers.IO) {
            todoItemsRepository.insertItem(todoItem)
        }
    }

    fun deleteTodo(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            todoItemsRepository.deleteItemById(id)
        }
    }

    fun onTodoBodyChanged(newValue: CharSequence) {
        val curValue = currentItemInternal.value
        newValue.toString().also {
            currentItemInternal.apply {
                todoBodyValidationInternal.update {_ ->
                    when (it.isBlank()) {
                        true -> ValidationEmpty
                        else -> ValidationOk
                    }
                }
                value.let { item ->
                    if (item.body != it) {
                        value = curValue.copy(body = it)
                    }
                }
            }
        }
    }
    fun onImportanceClick(importance: Importance) {
        menuVisibilityInternal.update { false }
        currentItemInternal.value.run {
            copy(
                importance = importance
            ).let {
                currentItemInternal.value = it
            }
        }
    }

    fun onDeadlineSwitchClick(isChecked: Boolean) {
        datePickerVisibilityInternal.update { isChecked }
        currentItemInternal.value.run {
            copy(
                deadline = null
            ).let {
                currentItemInternal.value = it
            }
        }
    }

    fun onDatePickerDismiss() {
        datePickerVisibilityInternal.update { false }
    }

    fun onDatePickerChange(date: Date) {
        datePickerVisibilityInternal.update { false }
        currentItemInternal.value.run {
            copy(
                deadline = date
            ).let {
                currentItemInternal.value = it
            }
        }
    }

    fun onMenuDismiss() {
        menuVisibilityInternal.update { false }
    }

    fun onMenuClick() {
        menuVisibilityInternal.update { it.not() }
    }

    private fun TodoItemImpl.mapToView() = ItemTodo(
        id, body, importance, deadline, isFinished, createDate, changeDate
    )
}