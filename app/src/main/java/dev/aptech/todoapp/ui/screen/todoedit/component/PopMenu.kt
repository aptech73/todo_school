package dev.aptech.todoapp.ui.screen.todoedit.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import dev.aptech.todoapp.R
import dev.aptech.todoapp.domain.model.Importance
import dev.aptech.todoapp.ui.apptheme.ToDoTheme

@Composable
fun PopMenu(
    importance: Importance,
    menuVisibility: Boolean,
    onMenuClick: () -> Unit,
    onDismissRequest: () -> Unit,
    onMenuItemClick: (Importance) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(ToDoTheme.colors.backPrimary)
            .fillMaxWidth()
            .padding(ToDoTheme.shape.paddingMedium)
            .clickable { onMenuClick() }
    ) {
        Text(
            text = stringResource(R.string.importance),
            style = ToDoTheme.typography.body,
            color = ToDoTheme.colors.labelPrimary
        )
        Text(
            text = when (importance) {
                Importance.HIGH -> stringResource(R.string.importance_high)
                Importance.LOW -> stringResource(R.string.importance_low)
                Importance.NORMAL -> stringResource(R.string.importance_normal)
            },
            style = ToDoTheme.typography.body,
            color = when (importance) {
                Importance.LOW, Importance.NORMAL -> ToDoTheme.colors.labelTertiary
                Importance.HIGH -> ToDoTheme.colors.colorRed
            }
        )
        DropdownMenu(
            expanded = menuVisibility,
            modifier = modifier
                .background(ToDoTheme.colors.backElevated),
            onDismissRequest = { onDismissRequest() }) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(R.string.importance_low),
                        style = ToDoTheme.typography.body
                    )
                },
                onClick = { onMenuItemClick(Importance.LOW) },
                colors = MenuDefaults.itemColors(
                    textColor = ToDoTheme.colors.labelPrimary,
                )
            )
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(R.string.importance_normal),
                        style = ToDoTheme.typography.body
                    ) },
                onClick = { onMenuItemClick(Importance.NORMAL) },
                colors = MenuDefaults.itemColors(
                    textColor = ToDoTheme.colors.labelPrimary,
                )
            )
            DropdownMenuItem(
                text = {
                    Text(
                        text = stringResource(R.string.importance_high),
                        style = ToDoTheme.typography.body
                    )
                },
                onClick = { onMenuItemClick(Importance.HIGH) },
                colors = MenuDefaults.itemColors(
                    textColor = ToDoTheme.colors.colorRed,
                )
            )
        }
    }
}