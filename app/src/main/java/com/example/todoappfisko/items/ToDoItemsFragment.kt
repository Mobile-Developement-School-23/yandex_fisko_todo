package com.example.todoappfisko.items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoappfisko.R
import com.example.todoappfisko.ToDoItemsAdapter
import com.example.todoappfisko.TodoItem
import com.example.todoappfisko.databinding.FragmentItemListBinding
import com.example.todoappfisko.item.ItemViewerFragment
import kotlinx.coroutines.launch

class ToDoItemsFragment : Fragment(R.layout.fragment_item_list),
    ToDoItemsAdapter.OnItemClickListener,
    ToDoItemsAdapter.OnItemRemoveListener {

    private val viewModel by viewModels<ToDoItemsViewModel>()

    private val toDoItemsAdapter = ToDoItemsAdapter(this, this)

    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemListBinding.inflate(layoutInflater)
        _binding?.let { initList(it) }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    toDoItemsAdapter.updateItems(state.items)
                }
            }
        }

        binding.addFab.setOnClickListener { openAddItemScreen(null) }
        return binding.root
    }

    private fun initList(binding: FragmentItemListBinding) {
        with(binding.toDoList) {
            adapter = toDoItemsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

    }

    private fun openAddItemScreen(item: TodoItem?) {
        parentFragmentManager?.let {
            it.beginTransaction()
                .replace(R.id.container, ItemViewerFragment.newInstance(item?.id))
                .addToBackStack("")
                .commit()
        }
    }

    override fun onItemClicked(item: TodoItem) {
        openAddItemScreen(item)
    }

    override fun onItemRemoved(item: TodoItem) {
        viewModel.removeItem(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}