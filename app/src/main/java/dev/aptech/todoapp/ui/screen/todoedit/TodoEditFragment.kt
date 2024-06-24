package dev.aptech.todoapp.ui.screen.todoedit

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.android.support.DaggerFragment
import dev.aptech.todoapp.R
import dev.aptech.todoapp.databinding.FragmentTodoeditBinding
import dev.aptech.todoapp.domain.model.Importance
import dev.aptech.todoapp.ui.screen.todoedit.validation.ValidationFailed
import dev.aptech.todoapp.ui.screen.todolist.TodoListViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

private const val TAG = "TodoEditFragment"

class TodoEditFragment: DaggerFragment(R.layout.fragment_todoedit) {

    private val args: TodoEditFragmentArgs by navArgs()

    private lateinit var binding: FragmentTodoeditBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[TodoEditViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTodoeditBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getItemById(args.todoId)

        setUI()

        observeTodoBodyValidation()
        observeDeadlineClick()
    }

    private fun observeTodoBodyValidation() {
        viewModel.todoBodyValidation.observe(viewLifecycleOwner) {
            (it as? ValidationFailed)?.let { binding.textBody.requestFocus() }
        }
    }

    private fun setUI() {
        binding.apply {
            close.setOnClickListener { findNavController().navigateUp() }
            save.setOnClickListener {
                viewModel?.saveTodo()
                findNavController().navigateUp()
            }
            deleteLayout.setOnClickListener {
                if (args.todoId.isNotEmpty()) viewModel?.deleteTodo(args.todoId)
                findNavController().navigateUp()
            }
            importanceLayout.setOnClickListener {
                val popup = PopupMenu(requireContext(), it)
                val inflater = popup.menuInflater
                inflater.inflate(R.menu.popup_menu, popup.menu)
                popup.show()
                popup.setOnMenuItemClickListener { item: MenuItem? ->
                    when (item!!.itemId) {
                        R.id.high -> { viewModel?.onImportanceClick(Importance.HIGH) }
                        R.id.low -> { viewModel?.onImportanceClick(Importance.LOW) }
                        R.id.normal -> { viewModel?.onImportanceClick(Importance.NORMAL) }
                    }
                    return@setOnMenuItemClickListener true
                }
            }
        }
    }

    private fun observeDeadlineClick() {
        viewModel.onDeadLineCLick.observe(viewLifecycleOwner) {
            when (binding.deadlineSwitch.isChecked) {
                false -> {
                    val picker = MaterialDatePicker.Builder.datePicker()
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build()
                    picker.addOnNegativeButtonClickListener {
                        viewModel.disableDeadline()
                    }
                    picker.addOnPositiveButtonClickListener {
                        viewModel.enableDeadline(Date(it))
                    }
                    picker.show(parentFragmentManager, TAG)
                }
                else -> {
                    Log.d(TAG, "disableDeadline")
                    viewModel.disableDeadline()
                }
            }
        }
    }
}