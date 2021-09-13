package com.example.exampleapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.exampleapp.R
import com.example.exampleapp.model.UserData
import java.util.ArrayList
import kotlin.reflect.KFunction1

class TodoListAdapter(
    var itemList: List<UserData>,
    val onClickListener: KFunction1<UserData, Unit>
) :
    RecyclerView.Adapter<TodoListAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflator = LayoutInflater.from(parent.context);
        val view = inflator.inflate(R.layout.todo_list_item, parent, false)
        return MyViewHolder(view);
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val userData = itemList.get(position)
        holder.textView.text = userData.first_name;
        holder.itemView.setOnClickListener {
            onClickListener(userData)
        }
    }

    override fun getItemCount(): Int {
        return itemList.count();
    }

    fun setValues(it: List<UserData>?) {
        if (it != null) {
            itemList = it
            notifyDataSetChanged()
        };
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textView);
    }

}