package dev.aptech.todoapp.ui.screen.todoedit.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.aptech.todoapp.R
import dev.aptech.todoapp.ui.apptheme.ToDoTheme
import dev.aptech.todoapp.ui.screen.todoedit.validation.Validation
import dev.aptech.todoapp.ui.screen.todoedit.validation.ValidationEmpty

@Composable
fun ToDoEditText(
    text: String,
    onValueChanged: (String) -> Unit,
    validation: Validation,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        colors = CardDefaults.cardColors(
            containerColor = ToDoTheme.colors.backSecondary
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = { onValueChanged(it) },
            textStyle = ToDoTheme.typography.body,
            placeholder = { 
                Text(
                    text = stringResource(R.string.text_field_help),
                    style = ToDoTheme.typography.body,
                    color = ToDoTheme.colors.labelTertiary
                )
            },
            minLines = 4,
            maxLines = 8,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = ToDoTheme.colors.colorBlue,
                focusedTextColor = ToDoTheme.colors.labelPrimary,
                unfocusedTextColor = ToDoTheme.colors.labelPrimary
            ),
            modifier = modifier.fillMaxWidth(),
            isError = validation is ValidationEmpty,
        )
    }
}