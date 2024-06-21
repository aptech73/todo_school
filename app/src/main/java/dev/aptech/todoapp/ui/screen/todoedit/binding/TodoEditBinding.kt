package dev.aptech.todoapp.ui.screen.todoedit.binding

import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import dev.aptech.todoapp.domain.model.Importance
import dev.aptech.todoapp.ui.screen.todoedit.validation.Validation
import dev.aptech.todoapp.ui.screen.todoedit.validation.ValidationEmpty
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val EMPTY_STRING = ""
private val dateFormatter = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

@BindingAdapter("todoBodyValidation")
fun setTodoBodyValidation(inputLayout: TextInputLayout, validation: Validation) {
    inputLayout.apply {
        error = when (validation) {
            is ValidationEmpty -> "Пустое поле"
            else -> EMPTY_STRING
        }
    }
}

@BindingAdapter("deadlineChecked")
fun setDeadlineChecked(switch: SwitchCompat, deadline: Date?) {
    switch.isChecked = when (deadline) {
        null -> false
        else -> true
    }
}

@BindingAdapter("deadlineText")
fun setDeadlineText(view: TextView, deadline: Date?) {
    when (deadline) {
        null -> view.visibility = View.GONE
        else -> {
            view.visibility = View.VISIBLE
            view.text = dateFormatter.format(deadline)
        }
    }
}

@BindingAdapter("textImportance")
fun setTextImportance(view: TextView, importance: Importance?) {
    if (importance != null){
        view.text = when (importance) {
            Importance.NORMAL -> "Нет"
            Importance.LOW -> "Низкий"
            Importance.HIGH -> "Высокий"
        }
    }
}