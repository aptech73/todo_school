package dev.aptech.todoapp.ui.screen.todoedit.component

import android.app.DatePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar
import java.util.Date

@Composable
fun ToDoDate(
    currentDate: Calendar,
    visibility: Boolean,
    onPositiveClick: (Date) -> Unit,
    onNegativeClick: () -> Unit
) {
    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            val chosenDate = Calendar.getInstance()
            chosenDate.set(year, month, dayOfMonth)
            onPositiveClick(chosenDate.time)
        },
        currentDate.get(Calendar.YEAR),
        currentDate.get(Calendar.MONTH),
        currentDate.get(Calendar.DAY_OF_MONTH)
    )

    datePickerDialog.setOnDismissListener { onNegativeClick() }
    datePickerDialog.setOnCancelListener { onNegativeClick() }

    when (visibility) {
        true -> datePickerDialog.show()
        else -> datePickerDialog.hide()
    }
}