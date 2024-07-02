package dev.aptech.todoapp.ui.screen.todoedit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import dev.aptech.todoapp.ui.component.ToDoEditBar

@Composable
fun ToDoEdit(
    todoId: String,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {

    val editTodoEditViewModel: TodoEditViewModel = hiltViewModel()

    val todoItem by editTodoEditViewModel.currentItem.collectAsState()

    LaunchedEffect(Unit) {
        editTodoEditViewModel.getItemById(todoId)
    }

    Scaffold(
        topBar = { ToDoEditBar(
            onCancelClick = { onBackPressed() },
            onSaveClick = {  },
            modifier = modifier
        ) }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
        ) {

        }
    }
}