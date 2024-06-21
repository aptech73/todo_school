package dev.aptech.todoapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.aptech.todoapp.R
import dev.aptech.todoapp.databinding.ItemTodoBinding
import dev.aptech.todoapp.domain.model.Importance
import dev.aptech.todoapp.domain.model.TodoItem
import dev.aptech.todoapp.generated.callback.OnClickListener
import dev.aptech.todoapp.ui.screen.todolist.model.ItemTodo

class TodoListAdapter(
    private val clickListener: OnItemClickListener
): ListAdapter<ItemTodo, TodoListAdapter.TodoListViewHolder>(TodoListDiffCallback) {

    interface OnItemClickListener {
        fun onItemClick(item: ItemTodo)
    }

    inner class TodoListViewHolder(
        private val binding: ItemTodoBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemTodo) {
            binding.item = item
            binding.root.setOnClickListener {
                clickListener.onItemClick(item)
            }
        }
    }

    private object TodoListDiffCallback : DiffUtil.ItemCallback<ItemTodo>() {
        override fun areItemsTheSame(oldItem: ItemTodo, newItem: ItemTodo): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ItemTodo, newItem: ItemTodo): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        return TodoListViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_todo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}