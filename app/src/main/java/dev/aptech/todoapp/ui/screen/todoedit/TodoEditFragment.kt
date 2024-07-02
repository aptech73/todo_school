package dev.aptech.todoapp.ui.screen.todoedit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import dev.aptech.todoapp.R
import dev.aptech.todoapp.databinding.FragmentTodoeditBinding
import dev.aptech.todoapp.ui.screen.todoedit.validation.ValidationFailed
import java.util.Date

private const val TAG = "TodoEditFragment"

class TodoEditFragment: Fragment(R.layout.fragment_todoedit) {

    private lateinit var binding: FragmentTodoeditBinding

    private val viewModel: TodoEditViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTodoeditBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //viewModel.getItemById(args.todoId)

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
                //if (args.todoId.isNotEmpty()) viewModel?.deleteTodo(args.todoId)
                findNavController().navigateUp()
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
                    viewModel.disableDeadline()
                }
            }
        }
    }
}