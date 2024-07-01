package dev.aptech.todoapp.ui.screen.todoedit

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import dev.aptech.todoapp.ui.component.ToDoEditBar

@Composable
fun ToDoEdit(
    todoId: String,
    onBackPressed: () -> Unit,
    modifier: Modifier
) {

    val editTodoEditViewModel = hiltViewModel<TodoEditViewModel>()

    Scaffold(
        topBar = { ToDoEditBar(
            onCancelClick = { onBackPressed() },
            onSaveClick = { /*TODO*/ },
            modifier = modifier
        ) }
    ) { paddingValues ->
        
    }
}