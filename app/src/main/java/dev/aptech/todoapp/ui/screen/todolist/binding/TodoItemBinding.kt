package dev.aptech.todoapp.ui.screen.todolist.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.databinding.BindingAdapter
import dev.aptech.todoapp.R
import dev.aptech.todoapp.domain.model.Importance
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private val dateFormatter = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())

@BindingAdapter("itemImportance")
fun setItemImportance(view: ImageView, todoImportance: Importance) {
    when (todoImportance) {
        Importance.NORMAL -> {
            view.visibility = View.GONE
        }
        Importance.LOW -> {
            view.visibility = View.VISIBLE
            view.setImageDrawable(AppCompatResources.getDrawable(view.context, R.drawable.ic_low_24))
        }
        Importance.HIGH -> {
            view.visibility = View.VISIBLE
            view.setImageDrawable(AppCompatResources.getDrawable(view.context, R.drawable.ic_high_24))
        }
    }
}

@BindingAdapter("itemSubhead")
fun setItemSubhead(view: TextView, deadline: Date?) {
    when (deadline) {
        null -> { view.visibility = View.GONE }
        else -> {
            view.visibility = View.VISIBLE
            view.text = dateFormatter.format(deadline)
        }
    }
}