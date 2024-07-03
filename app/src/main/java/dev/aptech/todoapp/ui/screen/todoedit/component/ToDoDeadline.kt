package dev.aptech.todoapp.ui.screen.todoedit.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import dev.aptech.todoapp.R
import dev.aptech.todoapp.ui.apptheme.ToDoTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val dateFormatter = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

@Composable
fun ToDoDeadline(
    deadline: Date?,
    onSwitchClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(ToDoTheme.shape.paddingMedium)
            .background(ToDoTheme.colors.backPrimary)
    ) {
        Column(
            modifier = modifier
                .weight(1f)
        ){
            Text(
                text = stringResource(R.string.deadline_help),
                style = ToDoTheme.typography.body,
                color = ToDoTheme.colors.labelPrimary

            )
            deadline?.let { 
                Text(
                    text = dateFormatter.format(deadline),
                    style = ToDoTheme.typography.subhead,
                    color = ToDoTheme.colors.colorBlue
                )
            }
        }
        Switch(
            checked = deadline != null,
            onCheckedChange = { onSwitchClick(it) },
            colors = SwitchDefaults.colors(
                checkedThumbColor = ToDoTheme.colors.colorBlue,
                checkedTrackColor = ToDoTheme.colors.colorBlue.copy(alpha = 0.3f),
                checkedBorderColor = Color.Transparent,
                uncheckedThumbColor = ToDoTheme.colors.backElevated,
                uncheckedTrackColor = ToDoTheme.colors.supportOverlay,
                uncheckedBorderColor = Color.Transparent,
            )
        )
    }
}