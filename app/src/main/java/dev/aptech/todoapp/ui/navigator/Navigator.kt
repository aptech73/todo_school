package dev.aptech.todoapp.ui.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.aptech.todoapp.ui.screen.todoedit.ToDoEdit
import dev.aptech.todoapp.ui.screen.todolist.ToDoList

private const val DESTINATION_LIST = "list"
private const val DESTINATION_EDIT = "edit"
private const val DESTINATION_EDIT_ARGS = "edit/{todoId}"


private const val ARGUMENT_ID = "todoId"

private const val EMPTY_ID = "new"

@Composable
fun NavHostController.InitNavigator() {
    NavHost(
        navController = this,
        startDestination = DESTINATION_LIST
    ) {
        composable(DESTINATION_LIST) {
            ToDoList(
                onListItemClick = { navigateToEdit(it) },
                onAddButtonClick = { navigateToEdit(EMPTY_ID) }
            )
        }
        composable(
            route = DESTINATION_EDIT_ARGS,
            arguments = listOf(navArgument(ARGUMENT_ID) { type = NavType.StringType })
        ) {
            ToDoEdit(
                todoId = it.arguments?.getString(ARGUMENT_ID) ?: EMPTY_ID,
                onBackPressed = { popBackStack() },
            )
        }
    }
}

fun NavHostController.navigateToEdit(todoId: String) {
    if (currentDestination?.route == DESTINATION_EDIT_ARGS) {
        return
    }
    navigate("$DESTINATION_EDIT/$todoId")
}