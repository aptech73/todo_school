package dev.aptech.todoapp.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.aptech.todoapp.ui.apptheme.ToDoTheme

@Composable
fun ToDoEditText(
    text: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier
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
    ) {

    }
}