package com.example.exampleapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList
import kotlin.reflect.KFunction1

class TodoListAdapter(var itemList: List<String>, val onClickListener: KFunction1<String, Unit>) :
    RecyclerView.Adapter<TodoListAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflator = LayoutInflater.from(parent.context);
        val view = inflator.inflate(R.layout.todo_list_item, parent, false)
        return MyViewHolder(view);
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val value = itemList.get(position)
        holder.textView.text = value;
        holder.itemView.setOnClickListener {
            onClickListener(value)
        }
    }

    override fun getItemCount(): Int {
        return itemList.count();
    }

    fun setValues(it: ArrayList<String>?) {
        if (it != null) {
            itemList = it
            notifyDataSetChanged()
        };
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView);
    }

}