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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.aptech.todoapp.ui.apptheme.ToDoTheme
import dev.aptech.todoapp.ui.component.PopMenu
import dev.aptech.todoapp.ui.component.ToDoDeadline
import dev.aptech.todoapp.ui.component.ToDoDelete
import dev.aptech.todoapp.ui.component.ToDoEditBar
import dev.aptech.todoapp.ui.component.ToDoEditText

@Composable
fun ToDoEdit(
    todoId: String,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {

    val editTodoEditViewModel: TodoEditViewModel = hiltViewModel()

    val todoItem by editTodoEditViewModel.currentItem.collectAsState()

    var menuVisibility by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        editTodoEditViewModel.getItemById(todoId)
    }

    Scaffold(
        topBar = { ToDoEditBar(
            onCancelClick = { onBackPressed() },
            onSaveClick = {  },
            modifier = modifier
        ) },
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
                })
            Spacer(modifier = modifier.height(ToDoTheme.shape.paddingSmall))
            PopMenu(
                importance = todoItem.importance,
                menuVisibility = menuVisibility,
                onMenuClick = { menuVisibility = menuVisibility.not() },
                onDismissRequest = { menuVisibility = menuVisibility.not() },
                onMenuItemClick = { editTodoEditViewModel.onImportanceClick(it) },
            )
            HorizontalDivider(thickness = 0.5.dp, color = ToDoTheme.colors.supportSeparator)
            ToDoDeadline(deadline = todoItem.deadline, onSwitchClick = { editTodoEditViewModel.onDeadlineClick() })
            HorizontalDivider(thickness = 0.5.dp, color = ToDoTheme.colors.supportSeparator)
            ToDoDelete(onDeleteClick = { editTodoEditViewModel.deleteTodo(todoId) })
        }
    }
}