package com.example.exampleapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.exampleapp.R
import com.example.exampleapp.databinding.TodoListItemBinding
import com.example.exampleapp.model.UserData

class TodoListAdapter(
    val onClickListener: (UserData) -> Unit
) : ListAdapter<UserData, TodoListAdapter.MyViewHolder>(UserDataComparator) {


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
        private var binding: TodoListItemBinding

        init {
            binding = TodoListItemBinding.bind(itemView);
            itemView.setOnClickListener { onClickListener(getItem(adapterPosition)) }
        }

        fun bind(userData: UserData) {
            binding.textView.text = "${userData.first_name} ${userData.last_name}"
        }
    }

    object UserDataComparator : DiffUtil.ItemCallback<UserData>() {
        override fun areItemsTheSame(oldItem: UserData, newItem: UserData) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UserData, newItem: UserData) = oldItem == newItem
    }

}