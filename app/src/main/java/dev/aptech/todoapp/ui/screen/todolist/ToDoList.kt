package dev.aptech.todoapp.ui.screen.todolist

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.aptech.todoapp.ui.apptheme.ToDoTheme
import dev.aptech.todoapp.ui.component.ToDoListBar
import dev.aptech.todoapp.ui.component.ToDoListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoList(
    onListItemClick: (String) -> Unit,
    onAddButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val todoListViewModel: TodoListViewModel = hiltViewModel()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
        rememberTopAppBarState()
    )

    val todoItems by todoListViewModel.items.collectAsState()
    val visibility by todoListViewModel.visibility.collectAsState()

    Scaffold(
        topBar = {
            ToDoListBar(
                scrollBehavior = scrollBehavior,
                onVisibleClick = { todoListViewModel.onVisibilityClick() },
                isVisibleAll = visibility)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onAddButtonClick() },
                containerColor = ToDoTheme.colors.colorBlue,
                contentColor = ToDoTheme.colors.colorWhite,
                shape = CircleShape
            ) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        },
        containerColor = ToDoTheme.colors.backPrimary,
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
    ){ paddingValues ->
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            colors = CardDefaults.cardColors(
                contentColor = ToDoTheme.colors.backSecondary
            ),
            modifier = Modifier
                .padding(top = ToDoTheme.shape.paddingSmall)
                .padding(ToDoTheme.shape.paddingSmall)
                .padding(paddingValues)
        ) {
            LazyColumn {
                items(todoItems) { currentItem ->
                    ToDoListItem(
                        todoItem = currentItem,
                        onCheckboxClick = { todoId, isFinished ->
                            todoListViewModel.setFinishedTodo(
                                todoId, isFinished
                            )
                        },
                        onItemClick = { todoId -> onListItemClick(todoId) }
                    )
                }
            }
        }
    }
}