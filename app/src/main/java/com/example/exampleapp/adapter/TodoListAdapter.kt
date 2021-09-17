package com.example.exampleapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.exampleapp.R
import com.example.exampleapp.databinding.TodoListItemBinding
import com.example.exampleapp.model.TodoItem

class TodoListAdapter(
    val onClickListener: (TodoItem) -> Unit
) : ListAdapter<TodoItem, TodoListAdapter.MyViewHolder>(UserDataComparator) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context);
        val view = inflater.inflate(R.layout.todo_list_item, parent, false)
        return MyViewHolder(view);
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userData = getItem(position)
        holder.bind(userData)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var binding: TodoListItemBinding = TodoListItemBinding.bind(itemView)

        init {
            itemView.setOnClickListener { onClickListener(getItem(adapterPosition)) }
        }

        fun bind(userData: TodoItem) {
            binding.textView.text = userData.title
        }
    }

    object UserDataComparator : DiffUtil.ItemCallback<TodoItem>() {
        override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem) = oldItem == newItem
    }

}