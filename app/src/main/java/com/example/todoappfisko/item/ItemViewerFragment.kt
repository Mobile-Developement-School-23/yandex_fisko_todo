package com.example.todoappfisko.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.todoappfisko.ToDoItemImportance
import com.example.todoappfisko.TodoItem
import com.example.todoappfisko.databinding.FragmentAddTaskBinding
import com.example.todoappfisko.extensions.withArguments
import kotlinx.coroutines.launch


class ItemViewerFragment : Fragment() {

    private val viewModel by viewModels<ItemViewerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAddTaskBinding.inflate(inflater, container, false)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    val item = state.item
                    initialization(binding, item)
                }
            }
        }

        val itemId = savedInstanceState?.getString(BUNDLE_KEY_ID)
        if (itemId != null) {
            viewModel.initialize(itemId)
        }

        binding.calendarView.visibility = View.GONE
        binding.switch1.setOnCheckedChangeListener { _, isCheked ->
            binding.calendarView.visibility = if (isCheked) View.VISIBLE else View.GONE
        }
        binding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            binding.dataFinish.text = "$dayOfMonth.${month + 1}.$year"
        }
        binding.buttonCancel.setOnClickListener {
            fragmentManager?.popBackStack()
        }

        binding.buttonSave.setOnClickListener {
            val newText = binding.itemText.text.toString()

            val importancePosition = binding.importanceSpinner.selectedItemPosition
            val newImportance = ToDoItemImportance.values()[importancePosition]

            viewModel.addItem(newText, newImportance)

            fragmentManager?.popBackStack()
        }
        return binding.root
    }

    private fun initialization(binding: FragmentAddTaskBinding, item: TodoItem?) {
        val text = item?.text ?: ""
        val importance = item?.importance ?: ToDoItemImportance.Normal

        binding.itemText.setText(text)

        val importanceSpinner = binding.importanceSpinner
        importanceSpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            ToDoItemImportance.values()
        )
        importanceSpinner.setSelection(importance.ordinal)
    }

    companion object {
        private const val BUNDLE_KEY_ID = "id"
        fun newInstance(bundleKeyId: String?): ItemViewerFragment {
            return ItemViewerFragment().withArguments {
                putString(BUNDLE_KEY_ID, bundleKeyId)
            }
        }
    }
}
