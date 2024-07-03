package dev.aptech.todoapp.ui.screen.todoedit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.aptech.todoapp.ui.apptheme.ToDoTheme
import dev.aptech.todoapp.ui.screen.todoedit.component.PopMenu
import dev.aptech.todoapp.ui.screen.todoedit.component.ToDoDate
import dev.aptech.todoapp.ui.screen.todoedit.component.ToDoDeadline
import dev.aptech.todoapp.ui.screen.todoedit.component.ToDoDelete
import dev.aptech.todoapp.ui.screen.todoedit.component.ToDoEditBar
import dev.aptech.todoapp.ui.screen.todoedit.component.ToDoEditText
import java.util.Calendar

@Composable
fun ToDoEdit(
    todoId: String,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {

    val editTodoEditViewModel: TodoEditViewModel = hiltViewModel()

    val todoItem by editTodoEditViewModel.currentItem.collectAsState()

    val datePickerVisibility by editTodoEditViewModel.datePickerVisibility.collectAsState()

    val menuVisibility by editTodoEditViewModel.menuVisibility.collectAsState()

    val validation by editTodoEditViewModel.todoBodyValidation.collectAsState()

    LaunchedEffect(Unit) {
        editTodoEditViewModel.getItemById(todoId)
    }

    Scaffold(
        topBar = {
            ToDoEditBar(
                todoId = todoId,
                onCancelClick = { onBackPressed() },
                onSaveClick = {
                    editTodoEditViewModel.saveTodo()
                    onBackPressed()
                },
                modifier = modifier
            )
        },
        containerColor = ToDoTheme.colors.backPrimary
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
        ) {
            Spacer(modifier = modifier.height(8.dp))
            ToDoEditText(
                text = todoItem.body,
                onValueChanged = {
                    editTodoEditViewModel.onTodoBodyChanged(it)
                },
                validation = validation
            )
            Spacer(modifier = modifier.height(ToDoTheme.shape.paddingSmall))
            PopMenu(
                importance = todoItem.importance,
                menuVisibility = menuVisibility,
                onMenuClick = { editTodoEditViewModel.onMenuClick() },
                onDismissRequest = { editTodoEditViewModel.onMenuDismiss() },
                onMenuItemClick = { editTodoEditViewModel.onImportanceClick(it) },
            )
            HorizontalDivider(thickness = 0.5.dp, color = ToDoTheme.colors.supportSeparator)
            ToDoDate(
                currentDate = Calendar.getInstance(),
                visibility = datePickerVisibility,
                onPositiveClick = { editTodoEditViewModel.onDatePickerChange(it) },
                onNegativeClick = { editTodoEditViewModel.onDatePickerDismiss() }
            )
            ToDoDeadline(
                deadline = todoItem.deadline,
                onSwitchClick = { editTodoEditViewModel.onDeadlineSwitchClick(it) }
            )
            HorizontalDivider(thickness = 0.5.dp, color = ToDoTheme.colors.supportSeparator)
            ToDoDelete(
                onDeleteClick = {
                    editTodoEditViewModel.deleteTodo(todoId)
                    onBackPressed()
                }
            )
        }
    }
}