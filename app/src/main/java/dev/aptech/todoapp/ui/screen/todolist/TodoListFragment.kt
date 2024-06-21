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
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import dev.aptech.todoapp.R
import dev.aptech.todoapp.databinding.FragmentTodolistBinding
import dev.aptech.todoapp.domain.model.TodoItem
import dev.aptech.todoapp.ui.adapter.TodoListAdapter
import dev.aptech.todoapp.ui.screen.todolist.model.ItemTodo
import kotlinx.coroutines.launch
import javax.inject.Inject

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

        binding.apply {
            todoList.layoutManager = LinearLayoutManager(requireContext())
            todoList.adapter = adapter

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