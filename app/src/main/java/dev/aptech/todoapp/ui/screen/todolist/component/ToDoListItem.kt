package dev.aptech.todoapp.ui.screen.todolist.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.aptech.todoapp.domain.model.Importance
import dev.aptech.todoapp.domain.model.TodoItemImpl
import dev.aptech.todoapp.ui.apptheme.ToDoTheme
import java.text.SimpleDateFormat
import java.util.Locale

private val dateFormatter = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

@Composable
fun ToDoListItem(
    todoItem: TodoItemImpl,
    onCheckboxClick: (String, Boolean) -> Unit,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(ToDoTheme.colors.backSecondary)
            .clickable { onItemClick(todoItem.id) },
        verticalAlignment = Alignment.Top
    ) {
        Checkbox(
            checked = todoItem.isFinished,
            onCheckedChange = { onCheckboxClick(todoItem.id, it) },
            colors = CheckboxDefaults.colors(
                uncheckedColor = when (todoItem.importance) {
                    Importance.HIGH -> ToDoTheme.colors.colorRed
                    else -> ToDoTheme.colors.supportSeparator
                },
                checkedColor = ToDoTheme.colors.colorGreen
            ),
            modifier = modifier
                .padding(start = 16.dp)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 12.dp, end = 12.dp, top = 12.dp, bottom = 12.dp)
        ) {
            Text(
                text = todoItem.body,
                color = when (todoItem.isFinished) {
                    false -> ToDoTheme.colors.labelPrimary
                    else -> ToDoTheme.colors.labelTertiary
                },
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = ToDoTheme.typography.body.copy(
                    textDecoration = when (todoItem.isFinished) {
                        true -> TextDecoration.LineThrough
                        else -> TextDecoration.None
                    }
                )
            )
            todoItem.deadline?.let {
                Text(
                    text = dateFormatter.format(todoItem.deadline),
                    color = ToDoTheme.colors.labelTertiary,
                    style = ToDoTheme.typography.subhead
                )
            }
        }
        Icon(
            imageVector = Icons.Outlined.Info,
            contentDescription = null,
            tint = ToDoTheme.colors.supportSeparator,
            modifier = Modifier
                .padding(end = 16.dp, top = 12.dp)
        )
    }
}