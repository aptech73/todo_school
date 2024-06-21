package dev.aptech.todoapp.ui.screen.todolist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.android.support.DaggerFragment
import dev.aptech.todoapp.R
import dev.aptech.todoapp.databinding.FragmentTodolistBinding
import dev.aptech.todoapp.domain.model.TodoItem
import dev.aptech.todoapp.ui.adapter.TodoListAdapter
import dev.aptech.todoapp.ui.screen.todolist.model.ItemTodo
import dev.aptech.todoapp.util.SwipeToDeleteCallback
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "TodoListFragment"

private const val EMPTY = ""

class TodoListFragment: DaggerFragment(R.layout.fragment_todolist) {

    private lateinit var binding: FragmentTodolistBinding
    private lateinit var adapter: TodoListAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[TodoListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = TodoListAdapter(object : TodoListAdapter.OnItemClickListener {
            override fun onItemClick(item: ItemTodo) {
                val direction = TodoListFragmentDirections.actionTodoListFragmentToTodoEditFragment(item.id)
                findNavController().navigate(direction)
            }
        }, object : TodoListAdapter.OnCheckBoxClickListener {
            override fun onCheckBoxClick(id: String, isChecked: Boolean) {
                viewModel.setFinishedTodo(id, isChecked)
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTodolistBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Log.d(TAG, "[SwipeToDeleteCallback] direction: $direction, viewHolder: $viewHolder")
                if (direction == 4) {
                    viewModel.removeTodo(adapter.currentList[viewHolder.adapterPosition].id)
                } else if (direction == 8) {
                    viewModel.setFinishedTodo(adapter.currentList[viewHolder.adapterPosition].id, true)
                }
            }
        }

        binding.apply {
            todoList.layoutManager = LinearLayoutManager(requireContext())
            todoList.adapter = adapter

            ItemTouchHelper(swipeHandler).attachToRecyclerView(todoList)

            addTodo.setOnClickListener {
                val direction = TodoListFragmentDirections.actionTodoListFragmentToTodoEditFragment(EMPTY)
                findNavController().navigate(direction)
            }


        }

        observeTodoItems()
        observeVisibility()
    }

    private fun observeTodoItems() {
        viewModel.items.observe(viewLifecycleOwner) {
            val count = it.count { itemTodo -> itemTodo.isFinished }
            binding.todoDoneCount.text = "Выполнено - $count"
            adapter.submitList(it)
        }
    }

    private fun observeVisibility() {
        viewModel.visibility.observe(viewLifecycleOwner) { visibility ->
            binding.visibilityButton.apply {
                when (visibility) {
                    true -> setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.ic_visibility_24))
                    else -> setImageDrawable(AppCompatResources.getDrawable(requireContext(), R.drawable.ic_visibility_off_24))
                }
            }
        }
    }
}