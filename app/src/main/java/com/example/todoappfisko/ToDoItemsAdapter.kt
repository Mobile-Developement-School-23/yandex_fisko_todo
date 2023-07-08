package com.example.todoappfisko

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoappfisko.extensions.inflate
import javax.inject.Inject

class ToDoItemsAdapter @Inject constructor(
    private val onItemRemoveListener: OnItemRemoveListener,
    private val onItemClickedListener: OnItemClickListener
) : RecyclerView.Adapter<ToDoItemsAdapter.Holder>() {

    private var todoItems: List<TodoItem> = emptyList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            parent.inflate(R.layout.list_item_todo),
            onItemRemoveListener,
            onItemClickedListener
        )
    }

    override fun getItemCount(): Int = todoItems.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = todoItems[position]
        holder.bind(item)
    }

    fun updateItems(newItems: List<TodoItem>) {
        todoItems = newItems
        notifyDataSetChanged()
    }

    class Holder @Inject constructor(
        view: View,
        private val onItemRemoveListener: OnItemRemoveListener,
        private val onItemClickedListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(view) {
            private var todoItem: TodoItem? = null
        private val textViewTask: TextView = view.findViewById(R.id.textViewTask)
        init {
            view.setOnClickListener {
                todoItem?.let { item -> onItemClickedListener.onItemClicked(item) }
            }
        }

        fun bind(item: TodoItem) {
            todoItem = item
            textViewTask.text = item.text
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(item: TodoItem)
    }
    interface OnItemRemoveListener {
        fun onItemRemoved(item: TodoItem)
    }
}