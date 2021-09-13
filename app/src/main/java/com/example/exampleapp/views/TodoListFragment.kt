package com.example.exampleapp.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.exampleapp.adapter.TodoListAdapter
import com.example.exampleapp.vm.TodoListViewModel
import com.example.exampleapp.databinding.FragmentTodoListBinding
import com.example.exampleapp.model.UserData

class TodoListFragment : Fragment() {
    private lateinit var binding: FragmentTodoListBinding
    private lateinit var todoListAdapter: TodoListAdapter
    private lateinit var viewModel: TodoListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTodoListBinding.inflate(inflater, container, false);
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(TodoListViewModel::class.java)

        todoListAdapter = TodoListAdapter(ArrayList(), this::onItemClicked)

        viewModel.refreshData()
        viewModel.stateModelLiveData.observe(viewLifecycleOwner, {
            resetState()
            if (!it.isLoading && !it.isError) {
                binding.recyclerView.visibility = View.VISIBLE
                todoListAdapter.setValues(it.data?.list);
            } else if (it.isLoading) {
                binding.swiperefresh.isRefreshing = true
            } else {
                binding.errorText.visibility = View.VISIBLE
                binding.errorText.text = it.errorMessage
            }
        });

        binding.swiperefresh.setOnRefreshListener {
            viewModel.refreshData()
        }

        binding.recyclerView.apply {
            adapter = todoListAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun resetState() {
        binding.recyclerView.visibility = View.GONE
        binding.swiperefresh.isRefreshing = false
        binding.errorText.visibility = View.GONE
    }

    fun onItemClicked(value: UserData) {
        Log.e("pass", value.first_name);
    }
}