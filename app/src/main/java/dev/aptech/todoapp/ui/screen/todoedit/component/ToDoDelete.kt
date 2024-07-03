package dev.aptech.todoapp.ui.screen.todoedit.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.aptech.todoapp.R
import dev.aptech.todoapp.ui.apptheme.ToDoTheme

@Composable
fun ToDoDelete(
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(ToDoTheme.colors.backPrimary)
            .padding(ToDoTheme.shape.paddingMedium)
            .clickable { onDeleteClick() }
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = null,
            tint = ToDoTheme.colors.colorRed
        )
        Text(
            text = stringResource(R.string.delete),
            style = ToDoTheme.typography.body,
            color = ToDoTheme.colors.colorRed,
            modifier = modifier
                .padding(start = 12.dp)
        )
    }
}