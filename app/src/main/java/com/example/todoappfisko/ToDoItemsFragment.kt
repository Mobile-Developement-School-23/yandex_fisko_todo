package com.example.todoappfisko

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoappfisko.databinding.FragmentItemListBinding
import com.fisko.yandex.todoapp.TodoItemsRepository


class ToDoItemsFragment : Fragment(R.layout.fragment_item_list),
    TodoItemsRepository.OnItemsChangeListener,
    ToDoItemsAdapter.OnItemClickListener,
    ToDoItemsAdapter.OnItemRemoveListener {

    private val toDoItemsAdapter = ToDoItemsAdapter(this, this)

    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemListBinding.inflate(layoutInflater)
        TodoItemsRepository.addItemsChangeListener(this)
        _binding?.let { initList(it) }
        val todoItems = TodoItemsRepository.todoItems

        binding.addFab.setOnClickListener { openAddItemScreen(null) }
        toDoItemsAdapter.updateItems(todoItems)
        toDoItemsAdapter.notifyItemRangeInserted(0, todoItems.size)
        return binding.root
    }

    private fun initList(binding: FragmentItemListBinding) {
        with(binding.toDoList) {
            adapter = toDoItemsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        TodoItemsRepository.removeItemsChangeListener(this)
    }

    private fun openAddItemScreen(item: TodoItem?) {

        childFragmentManager?.let {
            it.beginTransaction()
                .replace(R.id.container, ItemViewerFragment.newInstance(item?.id))
                .addToBackStack("")
                .commit()
        }
    }

    override fun onItemsChange(items: List<TodoItem>) {
        toDoItemsAdapter.updateItems(items)
    }

    override fun onItemClicked(item: TodoItem) {
        openAddItemScreen(item)
    }

    override fun onItemRemoved(item: TodoItem) {
        TodoItemsRepository.removeItem(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}