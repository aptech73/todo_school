package dev.aptech.todoapp.ui.screen.todolist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.findNavController
import dev.aptech.todoapp.R
import dev.aptech.todoapp.databinding.FragmentTodolistBinding
import dev.aptech.todoapp.ui.apptheme.AppTheme
import dev.aptech.todoapp.ui.apptheme.ToDoTheme
import dev.aptech.todoapp.ui.component.ToDoAppBar
import dev.aptech.todoapp.ui.component.ToDoListItem

private const val EMPTY = ""

class TodoListFragment : Fragment(R.layout.fragment_todolist) {

    private lateinit var binding: FragmentTodolistBinding

    private val viewModel: TodoListViewModel by activityViewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodolistBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.todoListLayout.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner))
            setContent {
                AppTheme {
                    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(
                        rememberTopAppBarState()
                    )

                    val todoList by viewModel.items.collectAsStateWithLifecycle(viewLifecycleOwner)
                    val isVisibleAll by viewModel.visibility.collectAsState()

                    Scaffold(
                        topBar = {
                            ToDoAppBar(
                                scrollBehavior = scrollBehavior,
                                onVisibleClick = {
                                    viewModel.onVisibilityClick()
                                },
                                isVisibleAll = isVisibleAll
                            )
                        },
                        floatingActionButton = {
                            FloatingActionButton(
                                onClick = {
                                    val direction =
                                        TodoListFragmentDirections.actionTodoListFragmentToTodoEditFragment(
                                            EMPTY
                                        )
                                    findNavController().navigate(direction)
                                },
                                containerColor = ToDoTheme.colors.colorBlue,
                                contentColor = ToDoTheme.colors.colorWhite,
                                shape = CircleShape
                            ) {
                                Icon(Icons.Filled.Add, contentDescription = null)
                            }
                        },
                        modifier = Modifier
                            .background(ToDoTheme.colors.backPrimary)
                            .nestedScroll(scrollBehavior.nestedScrollConnection)
                    ) { padding ->

                        ElevatedCard(
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 6.dp
                            ),
                            colors = CardDefaults.cardColors(
                                contentColor = ToDoTheme.colors.backSecondary
                            ),
                            modifier = Modifier
                                .padding(top = ToDoTheme.shape.paddingSmall)
                                .padding(ToDoTheme.shape.paddingSmall)
                                .padding(padding)
                        ) {
                            LazyColumn {
                                items(todoList) { currentItem ->
                                    ToDoListItem(
                                        todoItem = currentItem,
                                        onCheckboxClick = { todoId, isFinished ->
                                            viewModel.setFinishedTodo(
                                                todoId, isFinished
                                            )
                                        },
                                        onItemClick = { todoId ->
                                            val direction =
                                                TodoListFragmentDirections.actionTodoListFragmentToTodoEditFragment(todoId)
                                            findNavController().navigate(direction)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        return view
    }
}