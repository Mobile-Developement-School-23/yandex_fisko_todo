package com.example.todoappfisko

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.todoappfisko.Extensions.withArguments
import com.example.todoappfisko.databinding.FragmentAddTaskBinding
import com.fisko.yandex.todoapp.TodoItemsRepository


class ItemViewerFragment : Fragment() {

    private val toDoItemFactory = ToDoItemFactory()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        val item = getItem(savedInstanceState)
        if (item != null) {
            initialization(binding, item.text, item.importance)
        } else {
            initialization(binding, "", ToDoItemsImportance.Normal)
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
            val newImportance = ToDoItemsImportance.values()[importancePosition]

            val updatedItem = if (item != null) {
                item.copy(text = newText, importance = newImportance)
            } else {
                toDoItemFactory.create(newText, newImportance, false)
            }
            TodoItemsRepository.addItem(updatedItem)

            fragmentManager?.popBackStack()
        }
        return binding.root
    }

    private fun initialization(
        binding: FragmentAddTaskBinding,
        text: String,
        importance: ToDoItemsImportance
    ) {
        binding.itemText.setText(text)

        val importanceSpinner = binding.importanceSpinner
        importanceSpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            ToDoItemsImportance.values()
        )
        importanceSpinner.setSelection(importance.ordinal)
    }

    private fun getItem(savedInstanceState: Bundle?): TodoItem? {
        val itemID = savedInstanceState?.getString(BUNDLE_KEY_ID)
        return if (itemID != null) {
            TodoItemsRepository.getItem(itemID)
        } else {
            null
        }
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
