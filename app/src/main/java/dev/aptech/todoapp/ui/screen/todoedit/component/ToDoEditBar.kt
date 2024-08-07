package dev.aptech.todoapp.ui.screen.todoedit.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.aptech.todoapp.R
import dev.aptech.todoapp.ui.apptheme.ToDoTheme

private const val INVALID_ID = "new"

@Composable
fun ToDoEditBar(
    todoId: String,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(ToDoTheme.colors.backPrimary)
            .padding(ToDoTheme.shape.paddingMedium)
    ) {
        IconButton(onClick = { onCancelClick() }) {
            Icon(
                imageVector = Icons.Default.Close,
                tint = ToDoTheme.colors.labelPrimary,
                contentDescription = null,
            )
        }
        Text(
            text = stringResource(R.string.save).uppercase(),
            style = ToDoTheme.typography.button,
            color = when (todoId) {
                INVALID_ID -> ToDoTheme.colors.labelDisable
                else -> ToDoTheme.colors.colorBlue
            },
            modifier = modifier
                .clickable(
                    enabled = todoId != INVALID_ID
                ) { onSaveClick() }
        )
    }
}